package com.example.hateoas.controller;

import com.example.hateoas.domain.Product;
import com.example.hateoas.infra.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductRepository productRepository;

  @GetMapping("")
  public Flux<Product> findAll() {
    return this.productRepository.findAll();
  }

  @GetMapping("/{id}")
  public Mono<Product> findById(@PathVariable final Long id) {
    return this.productRepository.findById(id);
  }
}
