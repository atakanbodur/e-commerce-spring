package com.example.ecommercebackend.service;

import com.example.ecommercebackend.model.CartItem;
import com.example.ecommercebackend.model.CartItemRequest;
import com.example.ecommercebackend.model.Product;
import com.example.ecommercebackend.repository.CartItemRepository;
import com.example.ecommercebackend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService{

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductRepository productRepository;


    public CartItem addItemToCart(String token, CartItemRequest cartItemRequest) {
        Optional<Product> product = productRepository.findById(cartItemRequest.getProductid());

        if (!product.isPresent()) {
            throw new RuntimeException("Product not found");
        }

        List<CartItem> existingCartItems = cartItemRepository.findByTokenAndProductid(token, cartItemRequest.getProductid());

        if (!existingCartItems.isEmpty()) {
            CartItem existingCartItem = existingCartItems.get(0);
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemRequest.getQuantity());
            return cartItemRepository.save(existingCartItem);
        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.setToken(token);
            newCartItem.setProductid(cartItemRequest.getProductid());
            newCartItem.setQuantity(cartItemRequest.getQuantity());
            return cartItemRepository.save(newCartItem);
        }
    }


    public CartItem updateItemQuantity(CartItem cartItem) {
        CartItem existingCartItem = cartItemRepository.findById(cartItem.getCartitemid()).orElse(null);
        if (existingCartItem != null) {
            existingCartItem.setQuantity(cartItem.getQuantity());
            return cartItemRepository.save(existingCartItem);
        }
        return null;
    }


    public void removeItemFromCart(CartItem cartItem) {
        cartItemRepository.deleteById(cartItem.getCartitemid());
    }


    public List<CartItem> getCartItems(String token) {
        return cartItemRepository.findByToken(token);
    }

    public CartItem updateCartItemQuantity(int cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findByCartitemid(cartItemId);
        if (quantity > 0 ){
            cartItem.setQuantity(quantity);
            return cartItemRepository.save(cartItem);
        }
        else if(quantity == 0) {
            cartItemRepository.deleteById(cartItemId);
            return null;
        }
        else {
            return null;
        }
    }
}

