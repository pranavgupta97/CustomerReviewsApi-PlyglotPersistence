package com.udacity.course3.reviews;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.repository.mysqlRepository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void injectedComponentsAreNotNull() {
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(testEntityManager).isNotNull();
        assertThat(productRepository).isNotNull();
    }

    @Test
    public void testFindProductById() {
        Product sampleProduct = new Product();

        sampleProduct.setProductName("Head First Design Patterns Bruh!");

        entityManager.persist(sampleProduct);

        Optional<Product> realProduct = productRepository.findById(sampleProduct.getProductId());
        assertThat(realProduct).isNotNull();
        realProduct.ifPresent(product -> assertEquals(product.getProductName(), sampleProduct.getProductName()));
    }
}