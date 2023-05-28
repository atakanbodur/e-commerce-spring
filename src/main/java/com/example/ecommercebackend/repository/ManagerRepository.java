package com.example.ecommercebackend.repository;

import com.example.ecommercebackend.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {
    Manager findByManagerid(int id);
}
