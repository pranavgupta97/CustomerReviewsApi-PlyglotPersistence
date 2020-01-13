package com.udacity.course3.reviews;

import com.mongodb.MongoClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class ReviewsApplication {

	@Bean
	public MongoClient mongoClient() {
		return new MongoClient("localHost");
	}

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongoClient(), "customerreviews");
	}

	public static void main(String[] args) {
		SpringApplication.run(ReviewsApplication.class, args);
	}

}