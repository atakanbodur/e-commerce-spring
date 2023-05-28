package com.example.ecommercebackend.repository;

import com.example.ecommercebackend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findCommentsByCustomerId(int id);

    List<Comment> findCommentsByProductId(int id);

    Comment findCommentByCommentId(int id);


}
