package com.udacity.course3.reviews.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Document("reviews")
public class ReviewDocument {

    @Id
    private Integer reviewId;

    private String reviewTitle;

    private String reviewBody;

    private Integer productId;

    private List<Comment> comments = new ArrayList<>();

    public ReviewDocument(Review review) {
        this.reviewTitle = review.getReviewTitle();
        this.reviewBody = review.getReviewBody();
        this.productId = review.getProduct().getProductId();
    }

    public ReviewDocument() {

    }

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public String getReviewBody() {
        return reviewBody;
    }

    public void setReviewBody(String reviewBody) {
        this.reviewBody = reviewBody;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }
}