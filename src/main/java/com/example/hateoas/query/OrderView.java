package com.example.hateoas.query;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class OrderView extends RepresentationModel<OrderView> {

	private Long id;
	private String memberId;
	private String address;
	private Set<OrderLine> orderLines;

	@Data
	@NoArgsConstructor
	public static class OrderLine {
		private Product product;
		private int quantity;

		public OrderLine(Product product, int quantity) {
			this.product = product;
			this.quantity = quantity;
		}

		public OrderLine(com.example.hateoas.domain.OrderLine orderLine) {
			com.example.hateoas.domain.Product product = orderLine.getProduct();
			this.product = new OrderView.Product(product.getProductId(), product.getName(), product.getPrice());;
			this.quantity = orderLine.getQuantity();
		}

		public static Set<OrderLine> convertOrderLine(Set<com.example.hateoas.domain.OrderLine> orderLines) {
			Set<OrderLine> lineSet = new HashSet<>();
			for (com.example.hateoas.domain.OrderLine line : orderLines) {
				com.example.hateoas.domain.Product product = line.getProduct();

				OrderView.Product product1 = new OrderView.Product(product.getProductId(), product.getName(), product.getPrice());
				lineSet.add(new OrderView.OrderLine(product1, line.getQuantity()));
			}
			return lineSet;
		}
	}

	@Data
	@NoArgsConstructor
	public static class Product {
		private Long productId;
		private String name;
		private int price;

		public Product(Long productId, String name, int price) {
			this.productId = productId;
			this.name = name;
			this.price = price;
		}
	}
}
