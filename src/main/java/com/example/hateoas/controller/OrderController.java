package com.example.hateoas.controller;

import com.example.hateoas.application.OrderService;
import com.example.hateoas.command.OrderCommand;
import com.example.hateoas.query.OrderList;
import com.example.hateoas.query.OrderView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/v1/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @PostMapping("")
  public Mono<Boolean> createOrder(@RequestBody OrderCommand.Create create) {
    return this.orderService.create(create);
  }

  @PatchMapping("")
  public Mono<Boolean> changeAddress(@RequestBody OrderCommand.ChangeAddress changeAddress) {
    return this.orderService.changeAddress(changeAddress);
  }

  @DeleteMapping("/remove/{id}")
  public Mono<Boolean> removeOrder(@PathVariable Long id) {
    return this.orderService.delete(id);
  }

  @GetMapping("")
  public Flux<OrderList> orderList() {
    return this.orderService.findAll();
  }

  @GetMapping("/{id}")
  public Mono<OrderView> viewOrder(@PathVariable Long id) {
    return this.orderService.findById(id);
  }
}
