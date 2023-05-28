package com.example.ecommercebackend.model;

import jakarta.persistence.*;


@Entity
@Table(name = "managers")

public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int managerid;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "faEnabled")
    private boolean faEnabled;


    public int getManagerid() {return managerid;}

    public void setManagerid(int managerid) { this.managerid = managerid; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public boolean getFaEnabled() { return faEnabled; }

    public void setFaEnabled(boolean faEnabled) { this.faEnabled = faEnabled; }

    @Override
    public String toString() {
        return "Manager{" +
                "managerid=" + managerid +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password=" + password +
                ", faEnabled='" + faEnabled +
                '}';
    }
}
