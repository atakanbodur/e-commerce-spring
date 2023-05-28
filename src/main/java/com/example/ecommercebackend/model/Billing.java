package com.example.ecommercebackend.model;
import jakarta.persistence.*;


@Entity
@Table (name = "billings")
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int billingid;

    @Column(name = "customerid")
    private int customerid;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastname;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "state")
    private String state;

    @Column(name = "city")
    private String city;

    @Column(name = "zip")
    private int zip;

    @Column(name = "phone")
    private int phone;

    @Column(name = "ccNumber")
    private int ccNumber;

    @Column(name = "ccEndDate")
    private int ccEndDate;

    @Column(name = "ccCvv")
    private int ccCvv;

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public int getBillingid() {
        return billingid;
    }

    public void setBillingid(int billingid) {
        this.billingid = billingid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getCcNumber() {
        return ccNumber;
    }

    public void setCcNumber(int ccNumber) {
        this.ccNumber = ccNumber;
    }

    public int getCcEndDate() {
        return ccEndDate;
    }

    public void setCcEndDate(int ccEndDate) {
        this.ccEndDate = ccEndDate;
    }

    public int getCcCvv() {
        return ccCvv;
    }

    public void setCcCvv(int ccCvv) {
        this.ccCvv = ccCvv;
    }

}
