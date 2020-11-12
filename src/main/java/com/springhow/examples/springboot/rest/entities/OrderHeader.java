package com.springhow.examples.springboot.rest.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class OrderHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToMany(mappedBy = "orderHeader",cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetails = new ArrayList<>();
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void addOrderDetails(OrderDetails orderDetails){
        orderDetails.setOrderHeader(this);
        this.orderDetails.add(orderDetails);
    }
    public void removeOrderDetails(OrderDetails orderDetails){
        orderDetails.setOrderHeader(null);
        this.orderDetails.remove(orderDetails);
    }
}
