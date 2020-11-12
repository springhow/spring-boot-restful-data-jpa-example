package com.springhow.examples.springboot.rest.controller;

import com.springhow.examples.springboot.rest.entities.Item;
import com.springhow.examples.springboot.rest.entities.repositories.ItemRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemRepository itemRepository;

    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("/")
    CollectionModel<EntityModel<Item>> get() {
        List<EntityModel<Item>> items = itemRepository.findAll().stream().map(item -> EntityModel.of(item,
                linkTo(methodOn(ItemController.class).get(item.getId())).withSelfRel(),
                linkTo(methodOn(ItemController.class).get()).withRel("items")))
                .collect(Collectors.toList());
        return CollectionModel.of(items, linkTo(methodOn(ItemController.class).get()).withSelfRel());
    }

    @GetMapping("/{itemId}")
    EntityModel<Item> get(@PathVariable Integer itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(RuntimeException::new);
        return EntityModel.of(item,
                linkTo(methodOn(ItemController.class).get(itemId)).withSelfRel(),
                linkTo(methodOn(ItemController.class).get()).withRel("items"));
    }
}

