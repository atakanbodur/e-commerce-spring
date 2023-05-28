package com.example.ecommercebackend.model;

import jakarta.persistence.Column;

public class ProductReturnType {
    private int productid;
    private String name;
    private String description;
    private double price;
    private int categoryid;
    private String rating;
    private String image;
    private boolean status;
    private int storeid;
    private boolean hasCampaign = false;
    private double campaignPrice;
    private boolean disabled;

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getStoreid() {
        return storeid;
    }

    public void setStoreid(int storeid) {
        this.storeid = storeid;
    }

    public boolean isHasCampaign() {
        return hasCampaign;
    }

    public void setHasCampaign(boolean hasCampaign) {
        this.hasCampaign = hasCampaign;
    }

    public double getCampaignPrice() {
        return campaignPrice;
    }

    public void setCampaignPrice(double campaignPrice) {
        this.campaignPrice = campaignPrice;
    }
}
