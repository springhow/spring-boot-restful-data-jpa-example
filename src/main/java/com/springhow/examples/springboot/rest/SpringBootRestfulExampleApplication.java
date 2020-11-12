package com.springhow.examples.springboot.rest;

import com.springhow.examples.springboot.rest.entities.Item;
import com.springhow.examples.springboot.rest.entities.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
public class SpringBootRestfulExampleApplication implements CommandLineRunner {

	@Autowired
	ItemRepository itemRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestfulExampleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		itemRepository.save(new Item("Shoe", new BigDecimal("12.99")));
		itemRepository.save(new Item("Shirt", new BigDecimal("8.99")));
		itemRepository.save(new Item("Pants", new BigDecimal("9.99")));
	}
}
