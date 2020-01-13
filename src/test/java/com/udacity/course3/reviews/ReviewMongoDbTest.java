package com.udacity.course3.reviews;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.ReviewDocument;
import com.udacity.course3.reviews.repository.mongoRepository.ReviewMongoDbRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest
public class ReviewMongoDbTest {

    @Autowired
    ReviewMongoDbRepository reviewMongoDbRepository;

    @Test
    public void injectedComponentsAreNotNull() {
        assertThat(reviewMongoDbRepository).isNotNull();
    }

    @Test
    public void testFindAllByProductId() {

        Integer productId = 69;
        ReviewDocument reviewDocument = new ReviewDocument();
        reviewDocument.setProductId(productId);
        reviewDocument.setReviewTitle("Oooohh Yaaaayyy!");
        reviewDocument.setReviewBody("Dudes......... This is a good Product!");

        Comment comment = new Comment();
        comment.setCommentBody("Lol Nice Bruh! good to Know!");

        reviewDocument.addComment(comment);

        reviewMongoDbRepository.save(reviewDocument);

        Optional<List<ReviewDocument>> reviewDocuments = Optional.ofNullable(reviewMongoDbRepository.findAllByProductId(productId));
        assertThat(reviewDocuments).isNotNull();
        if (reviewDocuments.isPresent()) {
            assertEquals(reviewDocuments.get().size(), 1);
            assertEquals(reviewDocuments.get().get(0).getComments().get(0).getCommentBody(), comment.getCommentBody());
            assertEquals(reviewDocuments.get().get(0).getReviewTitle(), reviewDocument.getReviewTitle());
            assertEquals(reviewDocuments.get().get(0).getReviewBody(), reviewDocument.getReviewBody());
        }
    }
}