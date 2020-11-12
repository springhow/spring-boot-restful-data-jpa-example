package com.springhow.examples.springboot.rest.service;

import com.springhow.examples.springboot.rest.entities.*;
import com.springhow.examples.springboot.rest.entities.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderService {


    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public OrderHeader saveOrder(Cart cart) {
        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setStatus(OrderStatus.SUBMITTED);
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem cartItem : cart.getCartItems()) {
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setItem(cartItem.getItem());
            orderHeader.addOrderDetails(orderDetails);
            total = total.add(cartItem.getItem().getPrice());
        }
        orderHeader.setTotal(total);
        return orderRepository.save(orderHeader);
    }
}
