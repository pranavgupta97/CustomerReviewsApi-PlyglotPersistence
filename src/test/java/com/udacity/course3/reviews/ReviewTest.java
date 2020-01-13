package com.udacity.course3.reviews;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.mysqlRepository.ReviewRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReviewTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void injectedComponentsAreNotNull() {
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(testEntityManager).isNotNull();
        assertThat(reviewRepository).isNotNull();
    }

    @Test
    public void testFindReviewById() {
        Product sampleProduct = new Product();
        sampleProduct.setProductName("Cracking The Coding Interview Bruh!");

        Review sampleReview = new Review();
        sampleReview.setProduct(sampleProduct);
        sampleReview.setReviewTitle("Code Mode Dudes!");
        sampleReview.setReviewBody("That's some intense stuff Bruh!");

        entityManager.persist(sampleReview);

        Optional<Review> realReview = reviewRepository.findById(sampleReview.getReviewId());
        assertThat(realReview).isNotNull();
        if (realReview.isPresent()) {
            assertEquals(realReview.get().getReviewTitle(), sampleReview.getReviewTitle());
            assertEquals(realReview.get().getReviewBody(), sampleReview.getReviewBody());
        }
    }

    @Test
    public void testFindAllByProduct() {
        Product sampleProduct = new Product();
        sampleProduct.setProductName("StarBucks Coffee Bruh!");
        entityManager.persist(sampleProduct);

        Review sampleReview = new Review();
        sampleReview.setProduct(sampleProduct);
        sampleReview.setReviewTitle("Five Star Bruh!");
        sampleReview.setReviewBody("Coffee is some amazing stuff bruh!");

        entityManager.persist(sampleReview);

        Optional<List<Review>> realReviews = Optional.ofNullable(reviewRepository.findAllByProduct(sampleProduct));
        assertThat(realReviews).isNotNull();
        if (realReviews.isPresent()) {
            assertEquals(1, realReviews.get().size());
            assertEquals(realReviews.get().get(0).getReviewTitle(), sampleReview.getReviewTitle());;
            assertEquals(realReviews.get().get(0).getReviewBody(), sampleReview.getReviewBody());
        }
    }
}