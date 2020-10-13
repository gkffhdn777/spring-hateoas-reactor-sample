package com.example.hateoas.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.springframework.util.Assert;

/**
 * root domain
 */
@Getter
@ToString
@NoArgsConstructor
public class Order {

	private Long id;

	private String memberId;

	private Delivery delivery;

	private final Set<OrderLine> orderLines = new HashSet<>();

	public Order(Long id, String memberId, Delivery delivery, Set<OrderLine> orderLineList) {
		Assert.notNull(id , "Id cannot be null.");
		Assert.notNull(memberId, "MemberId cannot be null.");
		Assert.notNull(delivery, "Delivery cannot be null.");
		Assert.notNull(orderLineList, "checkoutItems cannot be null.");
		this.id = id;
		this.memberId = memberId;
		this.delivery = delivery;

		for (OrderLine orderItem : orderLineList) {
			this.withOrderLine(orderItem);
		}
	}

	/**
	 * 주문 수량은 무시
	 */
	private Order withOrderLine(OrderLine orderline) {
		for (OrderLine item : orderLines) {
			if (item.isProductEqual(orderline)) {
				item.merge(orderline);
				return this;
			}
		}
		this.orderLines.add(orderline);
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Order order = (Order) o;
		return Objects.equals(id, order.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
