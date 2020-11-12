package com.springhow.examples.springboot.rest.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String itemName;
    private BigDecimal price;

    public Item(String itemName, BigDecimal price) {
        this.itemName = itemName;
        this.price = price;
    }

    public Item() {

    }
}
