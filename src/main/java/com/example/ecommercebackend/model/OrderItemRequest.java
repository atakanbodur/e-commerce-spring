package com.example.ecommercebackend.model;

public class OrderItemRequest {
    private int productid;
    private int quantity;

    public OrderItemRequest() {
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
