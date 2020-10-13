package com.example.hateoas.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderList {
	private Long id;
	private String memberId;
	private int orderSize;
}
