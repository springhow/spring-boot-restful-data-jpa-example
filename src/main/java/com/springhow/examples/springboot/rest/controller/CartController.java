package com.springhow.examples.springboot.rest.controller;

import com.springhow.examples.springboot.rest.entities.*;
import com.springhow.examples.springboot.rest.entities.repositories.CartItemRepository;
import com.springhow.examples.springboot.rest.entities.repositories.CartRepository;
import com.springhow.examples.springboot.rest.entities.repositories.ItemRepository;
import com.springhow.examples.springboot.rest.service.OrderService;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;
    private final OrderService orderService;

    public CartController(CartRepository cartRepository, CartItemRepository cartItemRepository, ItemRepository itemRepository, OrderService orderService) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.itemRepository = itemRepository;
        this.orderService = orderService;
    }

    @GetMapping("/{cartId}")
    EntityModel<Cart> get(@PathVariable Integer cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(RuntimeException::new);
        return EntityModel.of(cart,
                linkTo(methodOn(CartController.class).get(cartId)).withSelfRel());
    }

    @PostMapping("/")
    EntityModel<Cart> create(@RequestBody Cart cart) {
        cart.setStatus(CartStatus.NEW);
        Cart created = cartRepository.save(cart);
        return EntityModel.of(created,
                linkTo(methodOn(CartController.class).get(created.getId())).withSelfRel());
    }

    @PutMapping("/{cartId}")
    EntityModel<Cart> update(@PathVariable Integer cartId) {
        Cart cartFromDB = cartRepository.findById(cartId).orElseThrow(RuntimeException::new);
        cartFromDB.setStatus(CartStatus.SUBMITTED);
        //Ignoring validation if the cart content has changed for simplicity.
        OrderHeader orderHeader = orderService.saveOrder(cartFromDB);
        return EntityModel.of(cartFromDB,
                linkTo(methodOn(CartController.class).get(cartFromDB.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).get(orderHeader.getId())).withRel("order")); // Link to the created order
    }

    @PostMapping(value = "/{cartId}/items/")
    EntityModel<Cart> update(@RequestBody Item request, @PathVariable Integer cartId) {

        Cart cart = cartRepository.findById(cartId).orElseThrow(RuntimeException::new);
        Item item = itemRepository.findById(request.getId()).orElseThrow(RuntimeException::new);
        CartItem cartItem = new CartItem();
        cartItem.setItem(item);
        cart.addCartItem(cartItem);
        Cart saved = cartRepository.save(cart);
        return EntityModel.of(saved,
                linkTo(methodOn(CartController.class).get(saved.getId())).withSelfRel(),
                linkTo(methodOn(ItemController.class).get(item.getId())).withRel("recentItemAdded"));
    }

    @DeleteMapping(value = "/{cartId}/items/{itemId}")
    EntityModel<Cart> deleteCartItem(@PathVariable Integer cartId, @PathVariable Integer itemId) {

        Cart cart = cartRepository.findById(cartId).orElseThrow(RuntimeException::new);
        CartItem cartItem = cartItemRepository.findById(itemId).orElseThrow(RuntimeException::new);
        cart.removeCartItem(cartItem);
        Cart saved = cartRepository.save(cart);

        return EntityModel.of(saved,
                linkTo(methodOn(CartController.class).get(saved.getId())).withSelfRel(),
                linkTo(methodOn(ItemController.class).get(cartItem.getItem().getId())).withRel("recentItemDeleted"));
    }
}
