package org.spring.study.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.spring.study.data.entity.Product;
import org.spring.study.data.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class ProductRepositoryIntegrateTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("Integrate Repository CRUD Test")
    public void basicCRUDTest() {

        /* create */
        // given
        Product givenProduct = Product.builder()
                .name("note")
                .price(1000)
                .stock(500)
                .build();

        // when
        Product savedProduct = productRepository.save(givenProduct);

        // then
        Assertions.assertThat(savedProduct.getNumber()).isEqualTo(givenProduct.getNumber());
        Assertions.assertThat(savedProduct.getName()).isEqualTo(givenProduct.getName());
        Assertions.assertThat(savedProduct.getPrice()).isEqualTo(givenProduct.getPrice());
        Assertions.assertThat(savedProduct.getStock()).isEqualTo(givenProduct.getStock());

        /* read */
        // when
        Product selectedProduct = productRepository.findById(savedProduct.getNumber()).orElseThrow(RuntimeException::new);

        // then
        Assertions.assertThat(selectedProduct.getNumber()).isEqualTo(givenProduct.getNumber());
        Assertions.assertThat(selectedProduct.getName()).isEqualTo(givenProduct.getName());
        Assertions.assertThat(selectedProduct.getPrice()).isEqualTo(givenProduct.getPrice());
        Assertions.assertThat(selectedProduct.getStock()).isEqualTo(givenProduct.getStock());

        /* update */
        // when
        Product foundProduct = productRepository.findById(selectedProduct.getNumber()).orElseThrow(RuntimeException::new);

        Product updateProduct = productRepository.save(Product.builder()
                .number(foundProduct.getNumber())
                .stock(foundProduct.getStock())
                .price(foundProduct.getPrice())
                .name("toy")
                .build());

        // then
        assertEquals(updateProduct.getName(), "toy");

        /* delete */
        // when
        productRepository.delete(updateProduct);

        // then
        assertFalse(productRepository.findById(selectedProduct.getNumber()).isPresent());
    }
}
