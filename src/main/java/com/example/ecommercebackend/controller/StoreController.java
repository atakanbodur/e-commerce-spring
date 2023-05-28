package com.example.ecommercebackend.controller;

import com.example.ecommercebackend.model.Campaign;
import com.example.ecommercebackend.model.Product;
import com.example.ecommercebackend.model.Store;
import com.example.ecommercebackend.repository.CampaignRepository;
import com.example.ecommercebackend.repository.ProductRepository;
import com.example.ecommercebackend.repository.StoreRepository;
import com.example.ecommercebackend.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/store")
public class StoreController {
    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreService storeService;


    @GetMapping("/stores")
    public List<Store> getAllEmployees() {
        return storeRepository.findAll();
    }

    @GetMapping("/{storeId}/products")
    public List<Product> getProductsByStoreId(@PathVariable int storeId) {
        return productRepository.findAllByStoreid(storeId);
    }

    @GetMapping("/{storeId}/getcampaigns")
    public List<Campaign> getCampaignsByStoreid(@PathVariable Integer storeid) {
        return storeService.getCampaignsByStoreid(storeid);
    }

    @GetMapping("/{storeId}/getproducts")
    public List<Product> getProductsByStoreid(@PathVariable Integer storeid) {
        return storeService.getProductsByStoreid(storeid);
    }

    @GetMapping("/stores/getstores")
    public List<Store> getAllStores() { return storeService.getAllStores(); }

}
