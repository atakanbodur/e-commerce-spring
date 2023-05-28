package com.example.ecommercebackend.controller;

import com.example.ecommercebackend.model.OrderItem;
import com.example.ecommercebackend.repository.OrderItemRepository;
import com.example.ecommercebackend.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/order_item")
public class OrderItemController {
    @Autowired
    private OrderItemRepository order_itemRepository;
    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("/order_items")
    public List<OrderItem> getAllEmployees() {
        return order_itemRepository.findAll();
    }

    @GetMapping("/storeowner/{storeOwnerId}")
    public ResponseEntity<List<OrderItem>> getOrderItemsByStoreOwnerId(@PathVariable Integer storeOwnerId) {
        List<OrderItem> orderItems = orderItemService.findOrderItemsByStoreOwnerId(storeOwnerId);
        return ResponseEntity.ok(orderItems);
    }
}