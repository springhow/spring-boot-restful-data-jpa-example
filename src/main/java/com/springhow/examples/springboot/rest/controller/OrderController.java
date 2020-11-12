package com.springhow.examples.springboot.rest.controller;

import com.springhow.examples.springboot.rest.entities.OrderHeader;
import com.springhow.examples.springboot.rest.entities.OrderStatus;
import com.springhow.examples.springboot.rest.entities.repositories.OrderRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/")
    List<OrderHeader> getOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    OrderHeader getOrder(@PathVariable Integer id) {
        return orderRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @PutMapping(value = "/{id}")
    OrderHeader updateOrder(@RequestBody OrderHeader request, @PathVariable Integer id) {
        OrderHeader order = orderRepository.findById(id).orElseThrow(RuntimeException::new);
        if (OrderStatus.CANCELLED.equals(request.getStatus())) {
            order.setStatus(OrderStatus.CANCELLED);
        }
        return orderRepository.save(order);
    }
}
