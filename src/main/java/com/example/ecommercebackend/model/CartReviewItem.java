package com.example.ecommercebackend.model;

public class CartReviewItem {
    private OrderItemRequest orderItem;
    private Campaign campaign;
    private Double price;
    private Double discount;
    private Double discountedPrice;

    public OrderItemRequest getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItemRequest orderItem) {
        this.orderItem = orderItem;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(Double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }
}
