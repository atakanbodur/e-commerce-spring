package com.example.ecommercebackend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "commentratings")
public class CommentRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentRatingId;

    @Column(name = "customerid")
    private int customerid;

    @Column(name = "commentId")
    private int commentId;

    @Column(name = "liked")
    private int liked;

    public int getCommentRatingId() {
        return commentRatingId;
    }

    public void setCommentRatingId(int commentRatingId) {
        this.commentRatingId = commentRatingId;
    }

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }
}
