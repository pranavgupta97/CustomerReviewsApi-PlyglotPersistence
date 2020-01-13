package com.udacity.course3.reviews.repository.mongoRepository;

import com.udacity.course3.reviews.entity.ReviewDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewMongoDbRepository extends MongoRepository<ReviewDocument, Integer> {

    List<ReviewDocument> findAllByProductId(Integer productId);
}