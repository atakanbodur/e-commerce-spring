package com.example.ecommercebackend.repository;

import com.example.ecommercebackend.model.Campaign;
import com.example.ecommercebackend.model.Customer;
import com.example.ecommercebackend.model.StoreOwner;
import com.example.ecommercebackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findCustomerByEmail(String email);
    Customer findCustomerByName(String name);


    Customer save(Customer customer);
    Customer findByToken(String token);

    Customer findCustomerByCustomerid(int customerid);
}