package org.spring.study.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.spring.study.data.dao.ProductDAO;
import org.spring.study.data.dto.ProductDto;
import org.spring.study.data.dto.ProductResponseDto;
import org.spring.study.data.entity.Product;
import org.spring.study.data.service.ProductService;
import org.spring.study.data.service.impl.ProductServiceImpl;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

    @MockBean
    ProductDAO productDAO;

    ProductService productService;

    @BeforeEach
    public void setUpTest() {
        productService = new ProductServiceImpl(productDAO);
    }

    @Test
    void saveProductTest() {
        Mockito.when(productDAO.insertProduct(any(Product.class))).then(returnsFirstArg());

        ProductResponseDto productResponseDto = productService.saveProduct(new ProductDto("pen", 1000, 1234));

        Assertions.assertEquals(productResponseDto.getName(), "pen");
        Assertions.assertEquals(productResponseDto.getPrice(), 1000);
        Assertions.assertEquals(productResponseDto.getStock(), 1234);

        verify(productDAO).insertProduct(any());
    }


}
