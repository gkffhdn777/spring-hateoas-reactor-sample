package com.example.hateoas.command;

import java.util.List;

import lombok.Data;

public final class OrderCommand {

	private OrderCommand() {
	}

	@Data
	public static final class Create {
		private final Long id;
		private final String memberId;
		private final List<OrderLine> orderLines;
		private final String address;
	}

	@Data
	public static final class ChangeAddress {
		private final Long id;
		private final String address;
	}

	@Data
	public static class OrderLine {
		private Product product;
		private int quantity;
	}

	@Data
	public static class Product {
		private Long productId;
		private String name;
		private int price;
	}
}
