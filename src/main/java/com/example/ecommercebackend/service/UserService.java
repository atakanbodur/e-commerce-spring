package com.example.ecommercebackend.service;

import com.example.ecommercebackend.config.TOTPClient;
import com.example.ecommercebackend.model.*;
import com.example.ecommercebackend.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private StoreOwnerRepository storeOwnerRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StoreRepository storeRepository;


    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerCustomer(Customer customer) throws UnsupportedEncodingException {
        // Check if email already exists
        Customer existingCustomerByEmail = customerRepository.findCustomerByEmail(customer.getEmail());
        if (existingCustomerByEmail != null) {
            throw new IllegalStateException("Email is already in use.");
        }

        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        if (customer.isFaEnabled()) {
            String key = TOTPClient.generateSecretKey();
            String qrCodeData = TOTPClient.getGoogleAuthenticatorBarCode(key, customer.getName());

            customer.setQrCodeData(qrCodeData);
            customer.setFaSecretKey(key);
        }
        return customerRepository.save(customer);
    }

    public User authenticateCustomer(String email, String password, String code) {
        Customer customer = customerRepository.findCustomerByEmail(email);
        System.out.println("Code is " + code);
        if (customer != null) {
            if (passwordEncoder.matches(password, customer.getPassword())) {
                System.out.println("Password matches");
                System.out.println("Server time: " + Instant.now());
                if (code==null && !customer.isFaEnabled()) {
                    return customer;
                }
                else {
                    boolean isCodeValid = TOTPClient.validateOTP(customer.getFaSecretKey(), code);
                    if (isCodeValid) {
                        System.out.println("Code is valid");
                        return customer;
                    } else {
                        System.out.println("Code is not valid");
                    }
                }
            } else {
                System.out.println("Password does not match");
            }
        } else {
            System.out.println("Customer not found");
        }
        return null;
    }

    public User registerStoreOwner(StoreOwner storeOwner) throws UnsupportedEncodingException {

        // Check if email already exists
        StoreOwner existingCustomerByEmail = storeOwnerRepository.findByEmail(storeOwner.getEmail());
        if (existingCustomerByEmail != null) {
            throw new IllegalStateException("Email is already in use.");
        }

        // Check if username already exists
        StoreOwner existingCustomerByUsername = storeOwnerRepository.findByName(storeOwner.getName());
        if (existingCustomerByUsername != null) {
            throw new IllegalStateException("Username is already in use.");
        }

        storeOwner.setPassword(passwordEncoder.encode(storeOwner.getPassword()));

        if (storeOwner.isFa_enabled()) {
            String key = TOTPClient.generateSecretKey();
            String qrCodeData = TOTPClient.getGoogleAuthenticatorBarCode(key, storeOwner.getName());

            storeOwner.setQrCodeData(qrCodeData);
            storeOwner.setFaSecretKey(key);
        }
        return storeOwnerRepository.save(storeOwner);
    }

    public User authenticateStoreOwner(String email, String password, String code) {
        StoreOwner storeOwner = storeOwnerRepository.findByEmail(email);
        System.out.println("Code is " + code);
        if (storeOwner != null) {
            if (passwordEncoder.matches(password, storeOwner.getPassword())) {
                System.out.println("Password matches");
                if (code==null && !storeOwner.isFa_enabled()) {
                    return storeOwner;
                }
                else {
                    boolean isCodeValid = TOTPClient.validateOTP(storeOwner.getFaSecretKey(), code);
                    if (isCodeValid) {
                        System.out.println("Code is valid");
                        return storeOwner;
                    } else {
                        System.out.println("Code is not valid");
                    }
                }
            } else {
                System.out.println("Password does not match");
            }
        } else {
            System.out.println("Customer not found");
        }
        return null;
    }

    public Customer editCustomerByCustomerId(Integer id, Customer updatedCustomer) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
        customer.setEmail(updatedCustomer.getEmail());
        customer.setAddress(updatedCustomer.getAddress());
        customer.setPhone(updatedCustomer.getPhone());
        customer.setName(updatedCustomer.getName());

        return customerRepository.save(customer);
    }
    public User checkCustomerCredentials(String email, String password) {
        Customer customer = customerRepository.findCustomerByEmail(email);
        if (customer != null) {
            if (passwordEncoder.matches(password, customer.getPassword())) {
                System.out.println("Password matches");
                    return customer;
            } else {
                System.out.println("Password does not match");
            }
        } else {
            System.out.println("Customer not found");
        }
        return null;
        }

    public User checkStoreOwnerCredentials(String email, String password) {
        StoreOwner storeOwner = storeOwnerRepository.findByEmail(email);
        if (storeOwner != null) {
            if (passwordEncoder.matches(password, storeOwner.getPassword())) {
                    return storeOwner;
            }
        }
        return null;
    }

    public Integer getCustomerIdByToken(String token) {
        Customer customer = customerRepository.findByToken(token);
        return customer.getCustomerid();
    }


    public boolean isAuthorizedCustomer(String token) {
        Customer customer = customerRepository.findByToken(token);
        return (customer != null);
    }

    public boolean isAuthorizedStoreOwner(String token) {
        StoreOwner owner = storeOwnerRepository.findByToken(token);
        return (owner != null);
    }

    public Customer changeCustomerPassword(Integer customerid, String newPassword) {
        Customer customer = customerRepository.findCustomerByCustomerid(customerid);
        if (customer != null ){
            System.out.println("Password before successfully for customer with ID: " + customer.getPassword());
            customer.setPassword(passwordEncoder.encode(newPassword));
            System.out.println("Password after successfully for customer with ID: " + customer.getPassword());
            return customerRepository.save(customer);
        } else {
            throw new IllegalArgumentException("Invalid customer name or password!");
        }
    }
    public StoreOwner changeStoreOwnerPassword(Integer storeOwnerId, String newPassword) {

        StoreOwner storeOwner = storeOwnerRepository.findStoreOwnerByStoreOwnerId(storeOwnerId);
        if ( storeOwnerId != null) {
            storeOwner.setPassword(passwordEncoder.encode(newPassword));
            System.out.println("Password updated successfully for StoreOwner with ID: " + storeOwner);
            return storeOwnerRepository.save(storeOwner);
        } else {
            throw new IllegalArgumentException("Invalid StoreOwner name or password!");
        }
    }

    public Product suspendProduct(int productid, int id) {
        Manager m = managerRepository.findByManagerid(id);
        if (m != null) {
            Optional<Product> p = productRepository.findById(productid);
            if (p.isPresent()) {
                p.get().setDisabled(true);
                productRepository.save(p.get());
                return p.get();
            }
            else return null;
        }
        else return null;
    }

    public Product unsuspendProduct(int productid, int id) {
        Manager m = managerRepository.findByManagerid(id);
        if (m != null) {
            Optional<Product> p = productRepository.findById(productid);
            if (p.isPresent()) {
                p.get().setDisabled(false);
                productRepository.save(p.get());
                return p.get();
            }
            else return null;
        }
        else return null;
    }

    public Store suspendStore(int storeid, int id) {
        Manager m = managerRepository.findByManagerid(id);
        if (m != null) {
            Optional<Store> s = storeRepository.findById(storeid);
            if (s.isPresent()) {
                s.get().setDisabled(true);
                List<Product> allProductsOfStore = productRepository.findAllByStoreid(storeid);
                for (Product p : allProductsOfStore) {
                    p.setDisabled(true);
                }
                productRepository.saveAll(allProductsOfStore);
                storeRepository.save(s.get());
                return s.get();
            } else return null;
        } else return null;
    }

    public Store unsuspendStore(int storeid, int id) {
        Manager m = managerRepository.findByManagerid(id);
        if (m != null) {
            Optional<Store> s = storeRepository.findById(storeid);
            if (s.isPresent()) {
                s.get().setDisabled(false);
                List<Product> allProductsOfStore = productRepository.findAllByStoreid(storeid);
                for (Product p : allProductsOfStore) {
                    p.setDisabled(false);
                }
                productRepository.saveAll(allProductsOfStore);
                storeRepository.save(s.get());
                return s.get();
            } else return null;
        } else return null;
    }


    public Customer enable2FACustomer(int customerid) throws UnsupportedEncodingException {
        Customer customer = customerRepository.findById(customerid).get();
        String key = TOTPClient.generateSecretKey();
        String qrCodeData = TOTPClient.getGoogleAuthenticatorBarCode(key, customer.getName());

        customer.setQrCodeData(qrCodeData);
        customer.setFaSecretKey(key);
        customer.setFa_enabled(true);
        customer.setFaEnabled(true);

        customerRepository.save(customer);
        return customer;
    }

    public StoreOwner enable2FAStoreOwner(int storeOwnerId) throws UnsupportedEncodingException {
        StoreOwner storeOwner = storeOwnerRepository.findById(storeOwnerId).get();
        String key = TOTPClient.generateSecretKey();
        String qrCodeData = TOTPClient.getGoogleAuthenticatorBarCode(key, storeOwner.getName());

        storeOwner.setQrCodeData(qrCodeData);
        storeOwner.setFaSecretKey(key);
        storeOwner.setFa_enabled(true);

        storeOwnerRepository.save(storeOwner);
        return storeOwner;
    }
}
