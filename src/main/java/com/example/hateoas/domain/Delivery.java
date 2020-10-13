package com.example.hateoas.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * value object
 */
@Getter
@AllArgsConstructor
@ToString
public class Delivery {
	private final String address;
}
