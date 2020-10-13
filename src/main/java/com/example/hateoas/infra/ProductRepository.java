package com.example.hateoas.infra;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;

import com.example.hateoas.domain.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

	private static final List<Product> PRODUCTS = new ArrayList<>();

	@PostConstruct
	private void init() {
		PRODUCTS.add(new Product(1L, "칫솔", 100));
		PRODUCTS.add(new Product(2L, "치약", 50));
		PRODUCTS.add(new Product(3L, "로션", 10));
		PRODUCTS.add(new Product(4L, "스킨", 20));
		PRODUCTS.add(new Product(5L, "면도기", 10));
		PRODUCTS.add(new Product(6L, "수건", 20));
		PRODUCTS.add(new Product(7L, "비누", 50));
		PRODUCTS.add(new Product(8L, "샴푸", 10));
	}

	public Flux<Product> findAll() {
		return Flux.fromIterable(PRODUCTS);
	}

	public Mono<Product> findById(final Long id) {
		return this.findAll().filter(product -> Objects.equals(product.getProductId(), id)).next();
	}
}
