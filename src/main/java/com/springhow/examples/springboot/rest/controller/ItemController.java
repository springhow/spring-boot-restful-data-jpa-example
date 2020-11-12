package com.springhow.examples.springboot.rest.controller;

import com.springhow.examples.springboot.rest.entities.Item;
import com.springhow.examples.springboot.rest.entities.repositories.ItemRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemRepository itemRepository;

    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("/")
    List<Item> get() {
        return itemRepository.findAll();
    }
}

