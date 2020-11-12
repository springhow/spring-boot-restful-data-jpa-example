package com.springhow.examples.springboot.rest.entities.repositories;

import com.springhow.examples.springboot.rest.entities.Item;
import com.springhow.examples.springboot.rest.entities.OrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

}
