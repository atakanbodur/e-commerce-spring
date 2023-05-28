package com.example.ecommercebackend.repository;

import com.example.ecommercebackend.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByToken(String token);
    List<CartItem> findByTokenAndProductid(String token, int productid);
    CartItem findByCartitemid(int cartItemId);
}
