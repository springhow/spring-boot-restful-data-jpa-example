package com.springhow.examples.springboot.rest.entities.repositories;

import com.springhow.examples.springboot.rest.entities.Cart;
import com.springhow.examples.springboot.rest.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

}
