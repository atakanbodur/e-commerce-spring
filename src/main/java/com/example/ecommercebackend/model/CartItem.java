package com.example.ecommercebackend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartitemid;

    @Column(name = "token")
    private String token;

    @Column(name = "productid")
    private int productid;

    @Column(name = "quantity")
    private int quantity;


    public int getCartitemid() {
        return cartitemid;
    }

    public void setCartitemid(int cartitemid) {
        this.cartitemid = cartitemid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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