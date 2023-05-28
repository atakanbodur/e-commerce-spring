package com.example.ecommercebackend.service;

import com.example.ecommercebackend.model.Comment;
import com.example.ecommercebackend.model.CommentRating;
import com.example.ecommercebackend.repository.CommentRatingRepository;
import com.example.ecommercebackend.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentRatingRepository commentRatingRepository;

    public List<Comment> getCommentsByCustomerId(int id) {
        return commentRepository.findCommentsByCustomerId(id);
    }

    public Comment insertComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByProductId(int id) {
        return commentRepository.findCommentsByProductId(id);
    }

    public CommentRating likeCommentByCustomerid(int customerid, int commentId) {

        Comment comment = commentRepository.findCommentByCommentId(commentId);
        CommentRating commentRating = commentRatingRepository.findByCustomeridAndCommentId(customerid, commentId);
        if (commentRating == null) {
            CommentRating commentRating1 = new CommentRating();
            commentRating1.setLiked(1);
            commentRating1.setCustomerid(customerid);
            commentRating1.setCommentId(commentId);
            commentRatingRepository.save(commentRating1);
            comment.setLikeCount(comment.getLikeCount() + 1);
            commentRepository.save(comment);
            return commentRatingRepository.findByCustomeridAndCommentId(customerid, commentId);
        }
        if (commentRating.getLiked() == 0) {

            commentRating.setLiked(1);
            commentRatingRepository.save(commentRating);
            comment.setLikeCount(comment.getLikeCount() + 1);
            comment.setDislikeCount(comment.getDislikeCount() - 1);
            commentRepository.save(comment);
            return commentRatingRepository.findByCustomeridAndCommentId(customerid, commentId);

        } else if (commentRating.getLiked() == 1) {
            commentRating.setLiked(-1);
            commentRatingRepository.save(commentRating);
            comment.setLikeCount(comment.getLikeCount() - 1);
            commentRepository.save(comment);
            return commentRatingRepository.findByCustomeridAndCommentId(customerid, commentId);

        } else {

            commentRating.setLiked(1);
            commentRatingRepository.save(commentRating);
            comment.setLikeCount(comment.getLikeCount() + 1);
            commentRepository.save(comment);
            return commentRatingRepository.findByCustomeridAndCommentId(customerid, commentId);
        }

    }

    public CommentRating dislikeCommentByCustomerid(int customerid, int commentId) {

        Comment comment = commentRepository.findCommentByCommentId(commentId);
        CommentRating commentRating = commentRatingRepository.findByCustomeridAndCommentId(customerid, commentId);

        if (commentRating == null) {
            CommentRating commentRating1 = new CommentRating();
            commentRating1.setLiked(0);
            commentRating1.setCustomerid(customerid);
            commentRating1.setCommentId(commentId);
            commentRatingRepository.save(commentRating1);
            comment.setDislikeCount(comment.getDislikeCount() + 1);
            commentRepository.save(comment);
            return commentRatingRepository.findByCustomeridAndCommentId(customerid, commentId);

        }
        if (commentRating.getLiked() == 0) {

            commentRating.setLiked(-1);
            commentRatingRepository.save(commentRating);
            comment.setDislikeCount(comment.getDislikeCount() - 1);

            commentRepository.save(comment);
            return commentRatingRepository.findByCustomeridAndCommentId(customerid, commentId);

        } else if (commentRating.getLiked() == 1) {
            commentRating.setLiked(0);
            commentRatingRepository.save(commentRating);
            comment.setDislikeCount(comment.getDislikeCount() + 1);
            comment.setLikeCount(comment.getLikeCount() - 1);
            commentRepository.save(comment);
            return commentRatingRepository.findByCustomeridAndCommentId(customerid, commentId);

        } else {

            commentRating.setLiked(0);
            commentRatingRepository.save(commentRating);
            comment.setDislikeCount(comment.getDislikeCount() + 1);
            commentRepository.save(comment);
            return commentRatingRepository.findByCustomeridAndCommentId(customerid, commentId);
        }

    }

}
