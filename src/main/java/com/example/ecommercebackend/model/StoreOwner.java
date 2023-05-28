package com.example.ecommercebackend.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "storeowners")
public class StoreOwner implements User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int storeOwnerId;

    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "fa_enabled")
    private boolean fa_enabled;

    @Column(name = "fa_secret_key")
    private String faSecretKey;

    @Column(name = "qr_code_data")
    private String qrCodeData;

    @Column(name = "role")
    private String role = "ROLE_STOREOWNER";

    @Column(name = "token")
    private String token;

    public int getStoreOwnerId() {
        return storeOwnerId;
    }

    public void setStoreOwnerId(int storeOwnerId) {
        this.storeOwnerId = storeOwnerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isFa_enabled() {
        return fa_enabled;
    }

    public void setFa_enabled(boolean fa_enabled) {
        this.fa_enabled = fa_enabled;
    }

    public String getFaSecretKey() {
        return faSecretKey;
    }

    public void setFaSecretKey(String faSecretKey) {
        this.faSecretKey = faSecretKey;
    }

    public String getQrCodeData() {
        return qrCodeData;
    }

    public void setQrCodeData(String qrCodeData) {
        this.qrCodeData = qrCodeData;
    }

    public int getId() {
        return storeOwnerId;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return "ROLE_STOREOWNER";
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
