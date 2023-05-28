package com.example.ecommercebackend.controller;

import com.example.ecommercebackend.model.Campaign;
import com.example.ecommercebackend.model.Customer;
import com.example.ecommercebackend.repository.CustomerRepository;
import com.example.ecommercebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customer")

public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/customers")
    public List<Customer> getAllEmployees() {
        return customerRepository.findAll();
    }

    @PutMapping("/customers/editCustomer/{id}")
    public Customer editCustomerByCustomerId(@PathVariable Integer id, @RequestBody Customer updatedCustomer) {
        return userService.editCustomerByCustomerId(id, updatedCustomer);
    }

    @GetMapping("/{customerid}")
    public Customer getCustomerById(@PathVariable int customerid) {
        return customerRepository.findById(customerid).get();
    }




}
