package com.example.hateoas.infra;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.example.hateoas.domain.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class OrderRepository {

	private final Mono<Queue<Order>> orders;

	OrderRepository() {
		this.orders = Mono.just(new ConcurrentLinkedQueue<>());
	}

	public Mono<Boolean> save(final Order order) {
		Assert.notNull(order, "Order cannot be null.");
		return orders.flatMap(queue -> Mono.just(queue.add(order)));
	}

	public Mono<Boolean> update(final Order order) {
		Assert.notNull(order, "Order cannot be null.");
		return this.orders.flatMap(queue ->
				deleteById(order.getId())
						.flatMap(x -> {
							if (x.equals(Boolean.TRUE)) {
								return Mono.just(queue.add(order));
							} else {
								return Mono.error(new RuntimeException("UPDATE order failure : id = " + order.getId()));
							}
						}));
	}

	public Mono<Boolean> deleteById(final Long id) {
		return this.orders.flatMap(queue -> Flux.fromIterable(queue)
				.filter(order -> Objects.equals(order.getId(), id))
				.flatMap(order -> Mono.just(queue.remove(order))).next());
	}

	public Mono<List<Order>> findAll() {
		return this.orders.flatMapMany(Flux::fromIterable)
				.collectSortedList(Comparator.comparingLong(Order::getId).reversed());
	}

	public Mono<Order> findById(final Long id) {
		return this.orders.flatMap(queue -> Flux.fromIterable(queue)
				.filter(order -> Objects.equals(order.getId(), id))
				.flatMap(Mono::just).next());
	}
}
