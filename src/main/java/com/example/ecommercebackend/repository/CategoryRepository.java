package com.example.ecommercebackend.repository;

import com.example.ecommercebackend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    List<Category> findByCategoryid(int categoryid);

}
