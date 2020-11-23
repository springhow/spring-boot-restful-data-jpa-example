package com.springhow.examples.springboot.rest.entities.repositories;

import com.springhow.examples.springboot.rest.entities.OrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "orders",itemResourceRel = "order", path = "orders")
public interface OrderRepository extends JpaRepository<OrderHeader, Integer> {

}
