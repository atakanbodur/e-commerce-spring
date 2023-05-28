package com.example.ecommercebackend.repository;

import com.example.ecommercebackend.model.Campaign;
import com.example.ecommercebackend.model.Product;
import com.example.ecommercebackend.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Integer> {
    List<Store> findAllByOwnerid(Integer id);

    List<Campaign> findCampaignsByStoreid(Integer storeid);

    List<Product> findProductsByStoreid(Integer storeid);

    List<Store> findAllStoresByStoreid (Integer storeid);
}
