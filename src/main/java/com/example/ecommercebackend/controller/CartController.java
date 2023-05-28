package com.example.ecommercebackend.controller;

import com.example.ecommercebackend.model.CartItem;
import com.example.ecommercebackend.model.CartItemRequest;
import com.example.ecommercebackend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/cart/add")
    public ResponseEntity<?> addItemToCart(@RequestBody CartItemRequest cartItemRequest, @RequestParam("token") String token) {
        try {
            cartService.addItemToCart(token, cartItemRequest);
            return new ResponseEntity<>("Item added to cart", HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/cartItem")
    public ResponseEntity<?> updateCartItemQuantity(@RequestParam int cartItemId, @RequestParam int quantity){
        try {
            CartItem c = cartService.updateCartItemQuantity(cartItemId, quantity);
            return new ResponseEntity<>(c, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<CartItem> updateItemQuantity(@RequestBody CartItem cartItem) {
        CartItem updatedCartItem = cartService.updateItemQuantity(cartItem);
        return ResponseEntity.ok(updatedCartItem);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeItemFromCart(@RequestBody CartItem cartItem) {
        cartService.removeItemFromCart(cartItem);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCartItems(@RequestParam("token") String token) {
        List<CartItem> cartItems = cartService.getCartItems(token);
        return ResponseEntity.ok(cartItems);
    }
}
