package com.example.ecommercebackend.repository;

import com.example.ecommercebackend.model.Customer;
import com.example.ecommercebackend.model.StoreOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreOwnerRepository extends JpaRepository<StoreOwner, Integer> {
    StoreOwner findByEmail(String email);
    StoreOwner findByName(String name);

    StoreOwner findByToken(String token);

    StoreOwner findStoreOwnerByStoreOwnerId(int storeOwnerId);
}
