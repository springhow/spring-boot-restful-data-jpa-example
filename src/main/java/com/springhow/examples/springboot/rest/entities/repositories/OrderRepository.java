package com.springhow.examples.springboot.rest.entities.repositories;

import com.springhow.examples.springboot.rest.entities.OrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderHeader, Integer> {

}
