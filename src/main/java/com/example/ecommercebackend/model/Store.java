package com.example.ecommercebackend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "stores")

public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int storeid;

    @Column(name = "ownerid")
    private int ownerid;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    private boolean status;

    @Column(name = "disabled")
    private boolean disabled;

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public int getStoreid() { return storeid; }

    public void setStoreid(int storeid) { this.storeid = storeid; }

    public int getOwnerid() { return ownerid; }

    public void setOwnerid(int ownerid) { this.ownerid = ownerid; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public boolean getStatus() { return status; }

    public void setStatus(boolean status) { this.status = status; }


    @Override
    public String toString() {
        return "Store{" +
                "storeid=" + storeid +
                ", ownerid='" + ownerid + '\'' +
                ", name='" + name + '\'' +
                ", desription=" + description +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", status=" + status +
                '}';
    }
}
