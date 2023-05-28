package com.example.ecommercebackend.service;

import com.example.ecommercebackend.controller.OrderPerMonthDTO;
import com.example.ecommercebackend.model.*;
import com.example.ecommercebackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private BillingRepository billingRepository;


    public Order postOrder(List<OrderItem> orderItems, Integer customerId, Integer billingid) {
        Order order = new Order();
        double totalPrice = 0;
        int  fixShippingPrice = 5;
        for (OrderItem orderItem : orderItems) {
            Integer campaignId = orderItem.getCampaignid();

            Optional<Product> productOptional = productRepository.findById(orderItem.getProductid());
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                double price = product.getPrice();
                Campaign campaign = null;

                if (campaignId != null) {
                    campaign = campaignRepository.findById(campaignId).orElse(null);
                }
                double discountedPrice = price;

                if (campaign != null && campaign.getProductId().equals(product.getProductid())) {
                    double discount = 0;
                    if (campaign.getDiscountType() == 1) {
                        discount = price * campaign.getDiscountAmount() / 100;
                    } else if (campaign.getDiscountType() == 2) {
                        discount = campaign.getDiscountAmount();
                    }
                    discountedPrice = price - discount;
                }

                orderItem.setPrice(discountedPrice);
                totalPrice += orderItem.getPrice() * orderItem.getQuantity() + fixShippingPrice;
            }
        }

        order.setTotalprice(totalPrice);
        order.setCustomerid(customerId);
        order.setOrderdate(String.valueOf(LocalDate.now()));
        order.setStatus("PENDING");
        order.setBillingid(billingid);

        Order savedOrder = orderRepository.save(order);

        for (OrderItem orderItem : orderItems) {
            orderItem.setOrderid(savedOrder.getOrderid());
            orderItemRepository.save(orderItem);
        }

        return savedOrder;
    }

    public Billing postBilling (Billing billing) {

         billingRepository.save(billing);
         return billing;

    }

    public List<CartReviewItem> getCartReview(List<OrderItemRequest> orderItems, List<Integer> campaignIds) {
        List<CartReviewItem> cartReviewItems = new ArrayList<>();

        // Retrieve all campaigns
        List<Campaign> campaigns = new ArrayList<>();
        if (campaignIds != null) {
            for (Integer campaignId : campaignIds) {
                Optional<Campaign> campaignOptional = campaignRepository.findById(campaignId);
                campaignOptional.ifPresent(campaigns::add);
            }
        }

        for (OrderItemRequest orderItem : orderItems) {
            CartReviewItem cartReviewItem = new CartReviewItem();
            cartReviewItem.setOrderItem(orderItem);

            Optional<Product> productOptional = productRepository.findById(orderItem.getProductid());
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                cartReviewItem.setPrice(product.getPrice());

                double bestDiscount = 0;
                Campaign bestCampaign = null;

                for (Campaign campaign : campaigns) {
                    if (campaign.getProductId().equals(product.getProductid())) {
                        double discount = 0;
                        if (campaign.getDiscountType() == 1) {
                            discount = product.getPrice() * campaign.getDiscountAmount() / 100;
                        } else if (campaign.getDiscountType() == 2) {
                            discount = campaign.getDiscountAmount();
                        }

                        if (discount > bestDiscount) {
                            bestDiscount = discount;
                            bestCampaign = campaign;
                        }
                    }
                }

                cartReviewItem.setCampaign(bestCampaign);
                cartReviewItem.setDiscount(bestDiscount);
                cartReviewItem.setDiscountedPrice(product.getPrice() - bestDiscount);
            }
            cartReviewItems.add(cartReviewItem);
        }
        return cartReviewItems;
    }

    public List<Order> getOrdersByCustomerId(int id) {
        return orderRepository.getOrdersByCustomerid(id);
    }

    public List<OrderPerMonthDTO> getOrdersPerMonth(LocalDate startDate, LocalDate endDate) {
        List<Object[]> ordersPerMonth = orderRepository.getOrdersPerMonth(startDate, endDate);

        return ordersPerMonth.stream()
                .map(obj -> new OrderPerMonthDTO((String) obj[0], (Long) obj[1]))
                .collect(Collectors.toList());
    }
}
