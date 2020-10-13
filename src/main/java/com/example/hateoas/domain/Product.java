package com.example.hateoas.domain;

import java.util.Objects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.springframework.util.Assert;

/**
 * other domain
 */
@Getter
@ToString
@NoArgsConstructor
public class Product {

	private Long productId;

	private String name;

	private int price;

	public Product(Long productId, String name, int price) {
		Assert.notNull(productId, "ProductId cannot be null.");
		Assert.notNull(name, "Name cannot be null.");
		Assert.notNull(price, "Price cannot be null.");
		this.productId = productId;
		this.name = name;
		this.price = price;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Product product = (Product) o;
		return Objects.equals(productId, product.productId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(productId);
	}
}
