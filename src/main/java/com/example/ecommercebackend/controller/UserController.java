package com.example.ecommercebackend.controller;

import com.example.ecommercebackend.model.*;
import com.example.ecommercebackend.repository.CartItemRepository;
import com.example.ecommercebackend.repository.CustomerRepository;
import com.example.ecommercebackend.repository.ManagerRepository;
import com.example.ecommercebackend.repository.StoreOwnerRepository;
import com.example.ecommercebackend.service.EmailService;
import com.example.ecommercebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private StoreOwnerRepository storeOwnerRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ManagerRepository managerRepository;


    @PostMapping("/register/customer")
    public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer) {
        try {
            customer.setToken(UUID.randomUUID().toString());
            Customer savedCustomer = (Customer) userService.registerCustomer(customer);
            emailService.sendWelcomeCustomerEmailAsync(customer);
            return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/login/manager")
    public ResponseEntity<?> loginManager(@RequestBody Manager m) {
        Optional<Manager> optionalManager = managerRepository.findById(1);

        if (optionalManager.isPresent()) {
            Manager manager = optionalManager.get();
            if (!manager.getEmail().equals(m.getEmail()) || !manager.getPassword().equals(m.getPassword())) {
                // return unauthorized
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            } else {
                // return ok
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } else {
            // return not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/login/customer")
    public ResponseEntity<Customer> loginCustomer(@RequestBody Map<String, String> loginData) {
        Customer customer = customerRepository.findCustomerByEmail(loginData.get("email"));

        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<CartItem> cartItems = cartItemRepository.findByToken(customer.getToken());
        Customer authenticatedCustomer;
        if (customer.isFaEnabled()) {
            authenticatedCustomer = (Customer) userService.authenticateCustomer(loginData.get("email"), loginData.get("password"), loginData.get("code"));
        } else {
            authenticatedCustomer = (Customer) userService.authenticateCustomer(loginData.get("email"), loginData.get("password"), null);
        }
        if (authenticatedCustomer == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        authenticatedCustomer.setToken(UUID.randomUUID().toString());

        for (CartItem c : cartItems) {
            c.setToken(authenticatedCustomer.getToken());
            cartItemRepository.save(c);
        }
        customerRepository.save(authenticatedCustomer);
        return new ResponseEntity<>(authenticatedCustomer, HttpStatus.OK);
    }

    @PostMapping("/register/storeowner")
    public ResponseEntity<StoreOwner> registerStoreOwner(@RequestBody StoreOwner storeOwner) {
        try {
            storeOwner.setToken(UUID.randomUUID().toString());
            StoreOwner savedCustomer = (StoreOwner) userService.registerStoreOwner(storeOwner);
            return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/login/storeowner")
    public ResponseEntity<StoreOwner> loginStoreOwner(@RequestBody Map<String, String> loginData) {
        StoreOwner storeOwner = storeOwnerRepository.findByEmail(loginData.get("email"));
        storeOwner.setToken(UUID.randomUUID().toString());
        if (storeOwner == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        StoreOwner authenticatedStoreOwner;
        if (storeOwner.isFa_enabled()) {
            authenticatedStoreOwner = (StoreOwner) userService.authenticateStoreOwner(loginData.get("email"), loginData.get("password"), loginData.get("code"));
        } else {
            authenticatedStoreOwner = (StoreOwner) userService.authenticateStoreOwner(loginData.get("email"), loginData.get("password"), null);
        }
        if (authenticatedStoreOwner == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        storeOwnerRepository.save(authenticatedStoreOwner);
        return new ResponseEntity<>(authenticatedStoreOwner, HttpStatus.OK);
    }

    @PostMapping("/check/customer")
    public ResponseEntity<Customer> checkCustomerCredentials(@RequestBody Map<String, String> loginData) {
        Customer customer = customerRepository.findCustomerByEmail(loginData.get("email"));
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<CartItem> cartItems = cartItemRepository.findByToken(customer.getToken());
        Customer authenticatedCheckCustomer;
        if (customer.isFaEnabled()) {
            authenticatedCheckCustomer = (Customer) userService.checkCustomerCredentials(loginData.get("email"), loginData.get("password") );
            if (authenticatedCheckCustomer == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(HttpStatus.PRECONDITION_REQUIRED);
        } else {
            authenticatedCheckCustomer = (Customer) userService.checkCustomerCredentials(loginData.get("email"), loginData.get("password") );
            if (authenticatedCheckCustomer == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            authenticatedCheckCustomer.setToken(UUID.randomUUID().toString());
            for (CartItem c : cartItems) {
                c.setToken(authenticatedCheckCustomer.getToken());
                cartItemRepository.save(c);
            }
            customerRepository.save(authenticatedCheckCustomer);
            return new ResponseEntity<>(authenticatedCheckCustomer, HttpStatus.OK);
        }
    }

    @PostMapping("/check/storeowner")
    public ResponseEntity<StoreOwner> checkStoreOwnerCredentials(@RequestBody Map<String, String> loginData) {
        StoreOwner storeOwner = storeOwnerRepository.findByEmail(loginData.get("email"));
        if (storeOwner == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        StoreOwner authenticatedCheckStoreOwner;
        if (storeOwner.isFa_enabled()) {
            authenticatedCheckStoreOwner = (StoreOwner) userService.checkStoreOwnerCredentials(loginData.get("email"), loginData.get("password"));
            if (authenticatedCheckStoreOwner == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(HttpStatus.PRECONDITION_REQUIRED);
        } else {
            authenticatedCheckStoreOwner = (StoreOwner) userService.checkStoreOwnerCredentials(loginData.get("email"), loginData.get("password"));
            if (authenticatedCheckStoreOwner == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            authenticatedCheckStoreOwner.setToken(UUID.randomUUID().toString());
            storeOwnerRepository.save(authenticatedCheckStoreOwner);
            return new ResponseEntity<>(authenticatedCheckStoreOwner, HttpStatus.OK);
        }
    }

    @GetMapping("/getCustomer/{token}")
    public ResponseEntity<Integer> getCustomerIdByToken(@PathVariable String token) {
        Integer customerId = userService.getCustomerIdByToken(token);
        return new ResponseEntity<>(customerId, HttpStatus.OK);
    }

    @PutMapping("/customers/password/{customerid}")
    public Customer changeCustomerPassword(@PathVariable Integer customerid, @RequestBody String newPassword) {
        Customer C = userService.changeCustomerPassword(customerid, newPassword);
        System.out.println(C.getPassword());
        return C;
    }

    @PutMapping("storeowner/password/{storeOwnerId}")
    public StoreOwner changeStoreOwnerPassword(@PathVariable Integer storeOwnerId, @RequestBody String newPassword) {

     StoreOwner S = userService.changeStoreOwnerPassword(storeOwnerId, newPassword);
        System.out.println(S.getPassword());
        return S;
    }

    @PutMapping("/suspend/product/{productid}")
    public Product suspendProduct(@PathVariable int productid, @RequestBody int id) {
        return userService.suspendProduct(productid, id);
    }

    @PutMapping("/unsuspend/product/{productid}")
    public Product unsuspendProduct(@PathVariable int productid, @RequestBody int id) {
        return userService.unsuspendProduct(productid, id);
    }

    @PutMapping("/suspend/store/{storeid}")
    public Store suspendStore(@PathVariable int storeid, @RequestBody int id) {
        return userService.suspendStore(storeid, id);
    }

    @PutMapping("/unsuspend/store/{storeid}")
    public Store unsuspendStore(@PathVariable int storeid, @RequestBody int id) {
        return userService.unsuspendStore(storeid, id);
    }

    @GetMapping("/customer/enable2FA/{customerid}")
    public Customer enable2FACustomer(@PathVariable int customerid) throws UnsupportedEncodingException {
        return userService.enable2FACustomer(customerid);
    }

    @GetMapping("/store/enable2FA/{storeownerid}")
    public StoreOwner enable2FAStoreOwner(@PathVariable int storeownerid) throws UnsupportedEncodingException {
        return userService.enable2FAStoreOwner(storeownerid);
    }
}
