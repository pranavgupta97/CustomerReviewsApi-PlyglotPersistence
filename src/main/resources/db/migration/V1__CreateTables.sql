 CREATE TABLE products (
    product_id INT AUTO_INCREMENT,
    product_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (product_id)
 );

 CREATE TABLE reviews (
    review_id INT AUTO_INCREMENT,
    review_title VARCHAR(255) NOT NULL,
    review_body VARCHAR(1000) NOT NULL,
    product_id INT,
    PRIMARY KEY (review_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
 );

 CREATE TABLE comments (
    comment_id INT AUTO_INCREMENT,
    comment_body VARCHAR(1000) NOT NULL,
    review_id INT,
    PRIMARY KEY (comment_id),
    FOREIGN KEY (review_id) REFERENCES reviews(review_id)
 );