package com.example.ecommercebackend.service;

import com.example.ecommercebackend.model.Campaign;
import com.example.ecommercebackend.model.Comment;
import com.example.ecommercebackend.model.Product;
import com.example.ecommercebackend.model.ProductReturnType;
import com.example.ecommercebackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CommentRepository commentRepository;
    @Autowired CampaignRepository campaignRepository;
    public List<ProductReturnType> searchProducts(String searchTerm) {
        List<ProductReturnType> returnTypes = new ArrayList<>();
        List<Product> products = productRepository.searchAll(searchTerm);
        for (Product p : products) {
            if (!p.isDisabled())
                returnTypes.add(setProductToProductReturnType(p));
        }
        return returnTypes;
    }

    public Optional<Product> getProductById(int productId) { return productRepository.findById(productId);
    }

    public List<Comment> getCommentListByProductid(int productid) {
        return productRepository.findCommentsByProductid(productid);
    }


    public List<ProductReturnType> getRecommendedProducts() {
        List<Product> products = productRepository.findTop10ByOrderByProductid();
        List<ProductReturnType> returnTypes = new ArrayList<>();
        for (Product p : products) {
            if (!p.isDisabled())
                returnTypes.add(setProductToProductReturnType(p));
        }
        return returnTypes;
    }


    public void uploadProductImage(int productId, MultipartFile file) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            try {
                product.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
                productRepository.save(product);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Product editCampaign(Integer id, Product updatedProduct) {
        Product product = productRepository.findByProductid(id);
        product.setCategoryid(updatedProduct.getCategoryid());
        product.setDescription(updatedProduct.getDescription());
        product.setImage(updatedProduct.getImage());
        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());
        product.setStatus(updatedProduct.getStatus());
        return productRepository.save(product);
    }

    public void addCommentToProduct(int productId, Comment comment) {

    }

    public List<ProductReturnType> getProductsByCategoryId(int id) {
        List<ProductReturnType> returnTypes = new ArrayList<>();
        List<Product> products = productRepository.findByCategoryid(id);
        for (Product p : products) {
            returnTypes.add(setProductToProductReturnType(p));
        }
        return returnTypes;
    }

    public List<ProductReturnType> getAllProducts(){
        List<Product> all = productRepository.findAll();
        List<ProductReturnType> returnTypes = new ArrayList<>();
        for (Product p:
             all) {
            ProductReturnType productReturnType = setProductToProductReturnType(p);
            returnTypes.add(productReturnType);
        }
        return returnTypes;
    }

    public ProductReturnType getProductByProductId(int id) {
        Product p = productRepository.findByProductid(id);
        return setProductToProductReturnType(p);
    }

    private ProductReturnType setProductToProductReturnType(Product p) {
        ProductReturnType productReturnType = new ProductReturnType();
        productReturnType.setProductid(p.getProductid());
        productReturnType.setName(p.getName());
        productReturnType.setDescription(p.getDescription());
        productReturnType.setPrice(p.getPrice());
        productReturnType.setCategoryid(p.getCategoryid());
        productReturnType.setRating(p.getRating());
        productReturnType.setImage(p.getImage());
        productReturnType.setStatus(p.getStatus());
        productReturnType.setStoreid(p.getStoreid());
        productReturnType.setDisabled(p.isDisabled());
        List<Campaign> campaigns = campaignRepository.findCampaignByProductId(productReturnType.getProductid());
        if (!campaigns.isEmpty()) {
            Campaign campaign = campaigns.get(0);
            productReturnType.setHasCampaign(true);
            /**
             * If discountType = 1 -> it's a percentage discount
             * If discountType = 2 -> it's an amount discount
             * Else error
             */
            Double discountType = campaign.getDiscountType();
            if (discountType == 1) {
                productReturnType.setCampaignPrice(productReturnType.getPrice() * campaign.getDiscountAmount() / 100);
            }
            else {
                productReturnType.setCampaignPrice(productReturnType.getPrice() - campaign.getDiscountAmount());
            }
        }
        return productReturnType;
    }


}
