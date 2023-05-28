package com.example.ecommercebackend.service;


import com.example.ecommercebackend.model.Campaign;
import com.example.ecommercebackend.model.Product;
import com.example.ecommercebackend.model.Store;
import com.example.ecommercebackend.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    public List<Campaign> getCampaignsByStoreid(Integer storeid) {
        return storeRepository.findCampaignsByStoreid(storeid);
    }

    public List<Product> getProductsByStoreid(Integer storeid) {
        return storeRepository.findProductsByStoreid(storeid);
    }

    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

}
