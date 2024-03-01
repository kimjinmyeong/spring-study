package org.spring.study.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.spring.study.data.entity.Product;
import org.spring.study.data.entity.QProduct;
import org.spring.study.data.repository.support.ProductRepository;
import org.spring.study.data.repository.QProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductQueryDSLTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    QProductRepository qProductRepository;

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @BeforeEach
    void setupProducts() {
        Product product1 = Product.builder()
                .name("ruler")
                .price(500)
                .stock(1000)
                .build();

        Product product2 = Product.builder()
                .name("note")
                .price(2000)
                .stock(100)
                .build();

        Product product3 = Product.builder()
                .name("pen")
                .price(5000)
                .stock(500)
                .build();

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
    }

    @AfterEach
    void cleanProducts() {
        productRepository.deleteAll();
    }

    @Test
    void queryDslTest() {
        QProduct qProduct = QProduct.product;

        List<String> productList = jpaQueryFactory
                .select(qProduct.name)
                .from(qProduct)
                .where(qProduct.name.eq("note"))
                .orderBy(qProduct.price.asc())
                .fetch();

        for (String product : productList) {
            System.out.println("Product Name : " + product);
        }
    }

    @Test
    void queryDSLTestUsingPredicate() {
        QProduct qProduct = QProduct.product;
        Predicate predicate = QProduct.product.name.containsIgnoreCase("pen").and(QProduct.product.price.between(500, 10000));

        Optional<Product> foundProduct = qProductRepository.findOne(predicate);

        if (foundProduct.isPresent()) {
            Product product = foundProduct.get();
            System.out.println(product.getName());
            System.out.println(product.getPrice());
            System.out.println(product.getStock());
        }

        Iterable<Product> productList = qProductRepository.findAll(
                qProduct.name.contains("ruler").and(qProduct.price.between(100, 1000))
        );

        for (Product product : productList) {
            System.out.println(product.getName());
            System.out.println(product.getPrice());
            System.out.println(product.getStock());
        }
    }

    @Test
    void queryDSLRepositoryTest() {
        List<Product> productList = productRepository.findByName("pen");

        for (Product product : productList) {
            System.out.println(product.getName());
            System.out.println(product.getPrice());
            System.out.println(product.getStock());
        }
    }

}
