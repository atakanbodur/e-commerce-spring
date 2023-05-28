package com.example.ecommercebackend.repository;

import com.example.ecommercebackend.model.Campaign;
import com.example.ecommercebackend.model.Comment;
import com.example.ecommercebackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT * FROM products WHERE " +
            "LOWER(name) LIKE LOWER(CONCAT('%',?1,'%')) OR " +
            "LOWER(description) LIKE LOWER(CONCAT('%',?1,'%')) OR " +
            "CAST(categoryid AS TEXT) LIKE CONCAT('%',?1,'%') OR " +
            "LOWER(rating) LIKE LOWER(CONCAT('%',?1,'%')) OR " +
            "LOWER(image) LIKE LOWER(CONCAT('%',?1,'%')) OR " +
            "CAST(storeid AS TEXT) LIKE CONCAT('%',?1,'%')", nativeQuery = true)
    List<Product> searchAll(String searchTerm);

    List<Product> findAllByStoreid(Integer storeOwnerId);

    List<Product> findByCategoryid(int categoryid);

    List<Product> findByPriceLessThanEqual(Double maxPrice);

    List<Product> findByPriceGreaterThanEqual(Double minPrice);

    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

    List<Product> findByRatingLessThanEqual(Double maxRating);

    List<Product> findByRatingGreaterThanEqual(Double minRating);

    List<Product> findByStatus(String status);

    List<Product> findByOrderByNameAsc();

    List<Comment> findCommentsByProductid(Integer productid);

    List<Product> findTop10ByOrderByProductid();

    Product findByProductid(int id);

}
