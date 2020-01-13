package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.entity.ReviewDocument;
import com.udacity.course3.reviews.repository.mysqlRepository.ProductRepository;
import com.udacity.course3.reviews.repository.mongoRepository.ReviewMongoDbRepository;
import com.udacity.course3.reviews.repository.mysqlRepository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with review entity.
 */
@RestController
public class ReviewsController {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final ReviewMongoDbRepository reviewMongoDbRepository;

    public ReviewsController(ReviewRepository reviewRepository, ProductRepository productRepository
                                , ReviewMongoDbRepository reviewMongoDbRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.reviewMongoDbRepository = reviewMongoDbRepository;
    }

    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.POST)
    public ResponseEntity<?> createReviewForProduct(@PathVariable("productId") Integer productId, @Valid @RequestBody Review review) {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {
            review.setProduct(product.get());
            reviewRepository.save(review);

            ReviewDocument reviewDocument = new ReviewDocument();
            reviewDocument.setReviewTitle(review.getReviewTitle());
            reviewDocument.setReviewBody(review.getReviewBody());
            reviewDocument.setProductId(productId);
            reviewDocument.setComments(review.getComments());

            reviewMongoDbRepository.save(reviewDocument);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.GET)
    public ResponseEntity<List<?>> listReviewsForProduct(@PathVariable("productId") Integer productId) {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {
            List<ReviewDocument> reviewDocuments = reviewMongoDbRepository.findAllByProductId(productId);
            List<Review> reviews = reviewRepository.findAllByProduct(product.get());
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}