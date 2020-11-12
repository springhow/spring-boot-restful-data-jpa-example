package com.springhow.examples.springboot.rest.controller;

import com.springhow.examples.springboot.rest.entities.OrderHeader;
import com.springhow.examples.springboot.rest.entities.OrderStatus;
import com.springhow.examples.springboot.rest.entities.repositories.OrderRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/")
    CollectionModel<EntityModel<OrderHeader>> get() {
        List<EntityModel<OrderHeader>> orders = orderRepository.findAll().stream().map(orderHeader -> EntityModel.of(orderHeader,
                linkTo(methodOn(ItemController.class).get(orderHeader.getId())).withSelfRel(),
                linkTo(methodOn(ItemController.class).get()).withRel("orders")))
                .collect(Collectors.toList());
        return CollectionModel.of(orders, linkTo(methodOn(ItemController.class).get()).withSelfRel());
    }

    @GetMapping("/{id}")
    EntityModel<OrderHeader> get(@PathVariable Integer id) {
        OrderHeader orderHeader = orderRepository.findById(id).orElseThrow(RuntimeException::new);
        return EntityModel.of(orderHeader,
                linkTo(methodOn(OrderController.class).get(id)).withSelfRel(),
                linkTo(methodOn(OrderController.class).get()).withRel("orders"));
    }

    @PutMapping(value = "/{id}")
    EntityModel<OrderHeader> update(@RequestBody OrderHeader request, @PathVariable Integer id) {
        OrderHeader order = orderRepository.findById(id).orElseThrow(RuntimeException::new);
        if (OrderStatus.CANCELLED.equals(request.getStatus())) {
            order.setStatus(OrderStatus.CANCELLED);
        }
        OrderHeader orderHeader = orderRepository.save(order);
        return EntityModel.of(orderHeader,
                linkTo(methodOn(OrderController.class).get(id)).withSelfRel(),
                linkTo(methodOn(OrderController.class).get()).withRel("orders"));
    }
}
