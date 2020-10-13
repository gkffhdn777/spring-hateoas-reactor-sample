package com.example.hateoas.application;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.example.hateoas.command.OrderCommand;
import com.example.hateoas.domain.Delivery;
import com.example.hateoas.domain.Order;
import com.example.hateoas.domain.OrderLine;
import com.example.hateoas.domain.Product;
import com.example.hateoas.infra.OrderRepository;
import com.example.hateoas.query.OrderList;
import com.example.hateoas.query.OrderView;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;

  public Mono<Boolean> create(final OrderCommand.Create create) {
    Set<OrderLine> orderLines = new HashSet<>();
    for (OrderCommand.OrderLine line : create.getOrderLines()) {
      OrderCommand.Product product = line.getProduct();
      orderLines.add(new OrderLine(new Product(product.getProductId(), product.getName(), product.getPrice()),
          line.getQuantity()));
    }

    Order order = new Order(create.getId(), create.getMemberId(), new Delivery(create.getAddress()), orderLines);
    return this.orderRepository.save(order);
  }

  public Mono<Boolean> changeAddress(final OrderCommand.ChangeAddress changeAddress) {
    return this.orderRepository.findById(changeAddress.getId())
        .flatMap(order -> {
          Order updateOrder = new Order(order.getId(), order.getMemberId(), new Delivery(changeAddress.getAddress()),
              order.getOrderLines());
          return orderRepository.update(updateOrder);
        });
  }

  public Mono<Boolean> delete(final Long id) {
    return this.orderRepository.deleteById(id);
  }

  public Flux<OrderList> findAll() {
    return this.orderRepository.findAll()
        .flatMapMany(Flux::fromIterable)
        .filter(Objects::nonNull)
        .flatMap(order -> Mono.just(new OrderList(order.getId(), order.getMemberId(), order.getOrderLines().size())))
        .defaultIfEmpty(new OrderList());
  }

  public Mono<OrderView> findById(final Long id) {
    return this.orderRepository.findById(id)
        .filter(Objects::nonNull)
        .flatMap(order -> Mono.just(new OrderView(order.getId(), order.getMemberId(), order.getDelivery().getAddress(),
            OrderView.OrderLine.convertOrderLine(order.getOrderLines()))))
        .defaultIfEmpty(new OrderView());
  }
}
