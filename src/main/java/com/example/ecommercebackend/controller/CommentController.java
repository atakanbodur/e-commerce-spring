package com.example.ecommercebackend.controller;

import com.example.ecommercebackend.model.Comment;
import com.example.ecommercebackend.model.CommentRating;
import com.example.ecommercebackend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @GetMapping("/user/{customerId}")
    public List<Comment> getCommentsByCustomerId(@PathVariable int customerId) {
        return commentService.getCommentsByCustomerId(customerId);
    }
    @PostMapping("/insert")
    public Comment insertComment(@RequestBody Comment comment) {
        return commentService.insertComment(comment);
    }
    @GetMapping("/product/{id}")
    public List<Comment> getCommentsByProductId(@PathVariable int id) {
        return commentService.getCommentsByProductId(id);
    }
    @PostMapping("/like/{customerid}/{commentId}")
    public CommentRating likeCommentByCustomerid(@PathVariable int customerid, @PathVariable int commentId) {
        return commentService.likeCommentByCustomerid(customerid ,commentId);
    }
    @PostMapping("/dislike/{customerid}/{commentId}")
    public CommentRating dislikeCommentByCustomerid(@PathVariable int customerid, @PathVariable int commentId) {
        return commentService.dislikeCommentByCustomerid(customerid, commentId);
    }
}
