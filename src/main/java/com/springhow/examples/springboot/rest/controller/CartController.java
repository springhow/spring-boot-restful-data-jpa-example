package com.springhow.examples.springboot.rest.controller;

import com.springhow.examples.springboot.rest.entities.Cart;
import com.springhow.examples.springboot.rest.entities.CartItem;
import com.springhow.examples.springboot.rest.entities.CartStatus;
import com.springhow.examples.springboot.rest.entities.Item;
import com.springhow.examples.springboot.rest.entities.repositories.CartItemRepository;
import com.springhow.examples.springboot.rest.entities.repositories.CartRepository;
import com.springhow.examples.springboot.rest.entities.repositories.ItemRepository;
import com.springhow.examples.springboot.rest.service.OrderService;
import org.springframework.web.bind.annotation.*;

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
    Cart get(@PathVariable Integer cartId) {
        return cartRepository.findById(cartId).orElseThrow(RuntimeException::new);
    }

    @PostMapping("/")
    Cart create(@RequestBody Cart cart) {
        cart.setStatus(CartStatus.NEW);
        return cartRepository.save(cart);
    }

    @PutMapping("/{cartId}")
    Cart update(@PathVariable Integer cartId) {
        Cart cartFromDB = cartRepository.findById(cartId).orElseThrow(RuntimeException::new);
        cartFromDB.setStatus(CartStatus.SUBMITTED);
        //Ignoring validation if the cart content has changed for simplicity.
        orderService.saveOrder(cartFromDB);
        return cartRepository.save(cartFromDB);
    }

    @PostMapping(value = "/{cartId}/items/")
    void update(@RequestBody Item request, @PathVariable Integer cartId) {

        Cart cart = cartRepository.findById(cartId).orElseThrow(RuntimeException::new);
        Item item = itemRepository.findById(request.getId()).orElseThrow(RuntimeException::new);
        CartItem cartItem = new CartItem();
        cartItem.setItem(item);
        cart.addCartItem(cartItem);
        cartRepository.save(cart);

    }

    @DeleteMapping(value = "/{cartId}/items/{itemId}")
    void deleteCartItem(@PathVariable Integer cartId, @PathVariable Integer itemId) {

        Cart cart = cartRepository.findById(cartId).orElseThrow(RuntimeException::new);
        CartItem cartItem = cartItemRepository.findById(itemId).orElseThrow(RuntimeException::new);
        cart.removeCartItem(cartItem);
        cartRepository.save(cart);

    }
}
