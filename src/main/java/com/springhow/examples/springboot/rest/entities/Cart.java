package com.springhow.examples.springboot.rest.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private CartStatus status;

    public void addCartItem(CartItem cartItem) {
        cartItem.setCart(this);
        this.cartItems.add(cartItem);
    }

    public void removeCartItem(CartItem cartItem) {
        cartItem.setCart(null);
        this.cartItems.remove(cartItem);
    }
}
