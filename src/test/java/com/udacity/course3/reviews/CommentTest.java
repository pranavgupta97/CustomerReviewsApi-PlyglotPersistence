package com.udacity.course3.reviews;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.mysqlRepository.CommentRepository;
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
public class CommentTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void injectedComponentsAreNotNull() {
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(testEntityManager).isNotNull();
        assertThat(commentRepository).isNotNull();
    }

    @Test
    public void testFindCommentById() {
        Product sampleProduct = new Product();
        sampleProduct.setProductName("FIFA 20 Bruh!");
        entityManager.persist(sampleProduct);

        Review sampleReview = new Review();
        sampleReview.setProduct(sampleProduct);
        sampleReview.setReviewTitle("Best Game Bruh!");
        sampleReview.setReviewBody("FIFA 20 is the best FEEFS so far Bruh!");
        entityManager.persist(sampleReview);

        Comment sampleComment = new Comment();
        sampleComment.setReview(sampleReview);
        sampleComment.setCommentBody("Thank you DUDE! We're Glad You Liked It Bruh!");

        entityManager.persist(sampleComment);

        Optional<Comment> realComment = commentRepository.findById(sampleComment.getCommentId());
        assertThat(realComment).isNotNull();
        realComment.ifPresent(comment -> assertEquals(comment.getCommentBody(), sampleComment.getCommentBody()));
    }

    @Test
    public void testFindAllCommentsByReview() {
        Product sampleProduct = new Product();
        sampleProduct.setProductName("Uncharted Bruh!");
        entityManager.persist(sampleProduct);

        Review sampleReview = new Review();
        sampleReview.setProduct(sampleProduct);
        sampleReview.setReviewTitle("Awesome Game Bruh!");
        sampleReview.setReviewBody("Uncharted is the best action game out there BRuH!");
        entityManager.persist(sampleReview);

        Comment sampleComment = new Comment();
        sampleComment.setReview(sampleReview);
        sampleComment.setCommentBody("Thank you for exploring the Uncharted BrUH!");

        entityManager.persist(sampleComment);

        Optional<List<Comment>> realComments = Optional.ofNullable(commentRepository.findAllByReview(sampleReview));
        assertThat(realComments).isNotNull();
        if (realComments.isPresent()) {
            assertEquals(1, realComments.get().size());
            assertEquals(realComments.get().get(0).getCommentBody(), sampleComment.getCommentBody());
        }
    }
}