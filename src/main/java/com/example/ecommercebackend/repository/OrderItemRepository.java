package com.example.ecommercebackend.repository;

import com.example.ecommercebackend.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByOrderid(int orderId);
    List<OrderItem> findAllByStoreid(int storeId);
}
