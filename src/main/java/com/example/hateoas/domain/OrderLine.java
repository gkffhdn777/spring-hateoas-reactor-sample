package com.example.hateoas.domain;

import lombok.Getter;
import lombok.ToString;

import org.springframework.util.Assert;

/**
 * order sub domain
 */
@Getter
@ToString
public class OrderLine {

	private final Product product;

	private int quantity;

	public OrderLine(Product product, int quantity) {
		Assert.notNull(product, "Product cannot be null.");
		Assert.state(quantity > 0, "Quantity must be greater than 0");
		this.product = product;
		this.quantity = quantity;
	}

	public void merge(OrderLine orderLine) {
		this.quantity += orderLine.quantity;
	}

	public boolean isProductEqual(OrderLine orderLine) {
		return this.product.equals(orderLine.product);
	}
}
