package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.entity.ReviewDocument;
import com.udacity.course3.reviews.repository.mysqlRepository.CommentRepository;
import com.udacity.course3.reviews.repository.mongoRepository.ReviewMongoDbRepository;
import com.udacity.course3.reviews.repository.mysqlRepository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with comment entity.
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {

    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewMongoDbRepository reviewMongoDbRepository;

    public CommentsController(CommentRepository commentRepository, ReviewRepository reviewRepository, ReviewMongoDbRepository reviewMongoDbRepository) {
        this.commentRepository = commentRepository;
        this.reviewRepository = reviewRepository;
        this.reviewMongoDbRepository = reviewMongoDbRepository;
    }

    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.POST)
    public ResponseEntity<?> createCommentForReview(@PathVariable("reviewId") Integer reviewId, @Valid @RequestBody Comment comment) {
        Optional<Review> review = reviewRepository.findById(reviewId);

        if (review.isPresent()) {
            comment.setReview(review.get());
            commentRepository.save(comment);

            Optional<ReviewDocument> reviewDocument = reviewMongoDbRepository.findById(review.get().getReviewId());

            reviewDocument.ifPresent(document -> document.addComment(comment));

            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.GET)
    public ResponseEntity<List<?>> listCommentsForReview(@PathVariable("reviewId") Integer reviewId) {
        Optional<Review> review = reviewRepository.findById(reviewId);

        if (review.isPresent()) {
            List<Comment> comments = commentRepository.findAllByReview(review.get());
            return ResponseEntity.ok(comments);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}