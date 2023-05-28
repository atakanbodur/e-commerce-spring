package com.example.ecommercebackend.controller;

import com.example.ecommercebackend.model.*;
import com.example.ecommercebackend.repository.CampaignRepository;
import com.example.ecommercebackend.repository.OrderItemRepository;
import com.example.ecommercebackend.repository.OrderRepository;
import com.example.ecommercebackend.repository.ProductRepository;
import com.example.ecommercebackend.service.EmailService;
import com.example.ecommercebackend.service.InvoiceService;
import com.example.ecommercebackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/order")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private OrderService orderService;
    @Autowired
    CampaignRepository campaignRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderItemRepository orderItemRepository;


    @GetMapping("/orders")
    public List<Order> getAllEmployees() {
        return orderRepository.findAll();
    }

    @GetMapping("/invoice/{orderId}")
    public ResponseEntity<ByteArrayResource> getInvoice(@PathVariable Integer orderId) {
        return invoiceService.getInvoice(orderId);
    }


    @PostMapping("/postOrder")
    public Order postOrder(@RequestBody List<OrderItem> orderItems, @RequestParam Integer customerId, @RequestParam Integer billingid) {
        Order order = orderService.postOrder(orderItems, customerId, billingid);
        emailService.sendCheckoutEmailAsync(order);
        return order;
    }
    @PostMapping("/postBilling")
    public Billing postBilling(@RequestBody Billing billing) {
        return orderService.postBilling(billing);

    }

    @PostMapping("/getCartReview")
    public List<CartReviewItem> getCartReview(@RequestBody List<OrderItemRequest> orderItems, @RequestParam(required = false) List<Integer> campaignIds) {
        return orderService.getCartReview(orderItems, campaignIds);
    }

    @GetMapping("/customer/{customerid}")
    public List<Order> getOrdersByCustomerId(@PathVariable int customerid) {
        return orderService.getOrdersByCustomerId(customerid);
    }

    @GetMapping("/sales/orderspermonth")
    public ResponseEntity<List<OrderPerMonthDTO>> getOrdersPerMonth(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<OrderPerMonthDTO> ordersPerMonth = orderService.getOrdersPerMonth(startDate, endDate);

        return ResponseEntity.ok(ordersPerMonth);
    }

}
