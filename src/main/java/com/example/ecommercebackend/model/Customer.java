package com.example.ecommercebackend.model;

import jakarta.persistence.*;


@Entity
@Table(name = "customers")

public class Customer implements User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerid;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "fa_enabled")
    private Boolean fa_enabled;

    @Column(name = "fa_secret_key")
    private String faSecretKey;

    @Column(name = "qr_code_data")
    private String qrCodeData;

    @Column(name = "role")
    private String role = "ROLE_CUSTOMER";

    @Column(name = "token")
    private String token;

    @Column(name = "new_password")
    private String new_password;

    // getters and setters
    public String getQrCodeData() {
        return qrCodeData;
    }

    public void setQrCodeData(String qrCodeData) {
        this.qrCodeData = qrCodeData;
    }

    public int getCustomerid() {return customerid; }

    public void setCustomerid(int customerid) { this.customerid = customerid; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    @Override
    public int getId() {
        return customerid;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public boolean isFaEnabled() { return fa_enabled; }

    public void setFaEnabled(boolean fa_enabled) { this.fa_enabled = fa_enabled; }

    public String getFaSecretKey() {
        return faSecretKey;
    }

    public void setFaSecretKey(String faSecretKey) {
        this.faSecretKey = faSecretKey;
    }

    public String getRole() {
        return "ROLE_CUSTOMER";
    }

    public Boolean getFa_enabled() {
        return fa_enabled;
    }

    public void setFa_enabled(Boolean fa_enabled) {
        this.fa_enabled = fa_enabled;
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


    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerid=" + customerid +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password=" + password +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", fa_enabled='" + fa_enabled +
                '}';
    }


}