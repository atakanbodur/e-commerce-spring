package com.example.ecommercebackend.controller;

import com.example.ecommercebackend.model.*;
import com.example.ecommercebackend.repository.ProductRepository;
import com.example.ecommercebackend.repository.StoreRepository;
import com.example.ecommercebackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("api/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public List<ProductReturnType> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/insert")
    public Product insertProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id){
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/editProduct/{id}")
    public Product editCampaign(@PathVariable Integer id, @RequestBody Product updatedProduct) {
        return productService.editCampaign(id, updatedProduct);
    }

    @GetMapping("/storeowners/{storeOwnerId}/products")
    public ResponseEntity<List<Product>> getProductsByStoreOwnerId(@PathVariable Integer storeOwnerId) {
        List<Product> products = new ArrayList<>();
        List<Store> stores = storeRepository.findAllByOwnerid(storeOwnerId);
        for (Store s : stores) {
            products.addAll(productRepository.findAllByStoreid(s.getStoreid()));
        }
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/products/category/{categoryid}")
    public List<ProductReturnType> getProductByCategoryid(@PathVariable int categoryid) {
        return productService.getProductsByCategoryId(categoryid);
    }

    @GetMapping("/products/max-price/{maxPrice}")
    public List<Product> getProductListByMaxPrice(@PathVariable Double maxPrice) {
        return productRepository.findByPriceLessThanEqual(maxPrice);
    }

    @GetMapping("/products/min-price/{minPrice}")
    public List<Product> getProductListByMinPrice(@PathVariable Double minPrice) {
        return productRepository.findByPriceGreaterThanEqual(minPrice);
    }

    @GetMapping("/products/min-max-price")
    public List<Product> getProductListByMinAndMax(@RequestParam Double minPrice, @RequestParam Double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    @GetMapping("/products/max-rating/{maxRating}")
    public List<Product> getProductListByMaxRating(@PathVariable Double maxRating) {
        return productRepository.findByRatingLessThanEqual(maxRating);
    }

    @GetMapping("/products/min-rating/{minRating}")
    public List<Product> getProductListByMinRating(@PathVariable Double minRating) {
        return productRepository.findByRatingGreaterThanEqual(minRating);
    }

    @GetMapping("/products/status/{status}")
    public List<Product> getProductListByStatus(@PathVariable String status) {
        return productRepository.findByStatus(status);
    }

    @GetMapping("/products/store/{storeId}")
    public List<Product> getProductListByStoreId(@PathVariable Integer storeId) {
        return productRepository.findAllByStoreid(storeId);
    }

    @GetMapping("/products/alphabetic-order")
    public List<Product> getProductListByAlphabeticOrder() {
        return productRepository.findByOrderByNameAsc();
    }

    @GetMapping("/products/{productId}")
    public ProductReturnType getProductById(@PathVariable int productId) {
        return productService.getProductByProductId(productId);
    }

    @PostMapping("/products/{productId}/comments")
    public ResponseEntity<Void> postUsersCommentToProduct(@PathVariable int productId, @RequestBody Comment comment) {

        productService.addCommentToProduct(productId, comment);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/products/{productid}/comments")
    public List<Comment> getCommentListByProductid(@PathVariable int productid) {
        return productService.getCommentListByProductid(productid);
    }


    @GetMapping("/products/recommended")
    public List<ProductReturnType> getRecommendedProducts() {
        return productService.getRecommendedProducts();
    }

    @GetMapping("/search/{searchTerm}")
    public List<ProductReturnType> searchAllProducts(@PathVariable String searchTerm) {
        return productService.searchProducts(searchTerm);
    }

    @PostMapping("/products/{productId}/images")
    public ResponseEntity<Void> uploadProductImage(@PathVariable int productId, @RequestParam("file") MultipartFile file) {
        productService.uploadProductImage(productId, file);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}

