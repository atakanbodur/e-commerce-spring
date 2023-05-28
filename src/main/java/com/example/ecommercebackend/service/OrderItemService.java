package com.example.ecommercebackend.service;

import com.example.ecommercebackend.model.OrderItem;
import com.example.ecommercebackend.model.Store;
import com.example.ecommercebackend.repository.OrderItemRepository;
import com.example.ecommercebackend.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<OrderItem> findOrderItemsByStoreOwnerId(int storeOwnerId) {
        List<Store> stores = storeRepository.findAllByOwnerid(storeOwnerId);
        List<Integer> storeIds = stores.stream().map(Store::getStoreid).collect(Collectors.toList());
        List<OrderItem> orderItems = new ArrayList<>();
        for (int storeid : storeIds) {
            orderItems.addAll(orderItemRepository.findAllByStoreid(storeid));
        }
        return orderItems;
    }

}
