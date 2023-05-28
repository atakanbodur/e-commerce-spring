package com.example.ecommercebackend.repository;
import com.example.ecommercebackend.model.Comment;
import com.example.ecommercebackend.model.CommentRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface CommentRatingRepository extends JpaRepository<CommentRating, Integer> {

    CommentRating findByCustomeridAndCommentId(int customerid, int commentId);
}