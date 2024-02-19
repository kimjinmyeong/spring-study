package org.spring.study.data.service.impl;

import org.spring.study.data.dao.ProductDAO;
import org.spring.study.data.dto.ProductDto;
import org.spring.study.data.dto.ProductResponseDto;
import org.spring.study.data.entity.Product;
import org.spring.study.data.service.ProductService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;

    public ProductServiceImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public ProductResponseDto getProduct(Long number) {
        Product product = productDAO.selectProduct(number);

        ProductResponseDto productResponseDto = ProductResponseDto.builder()
                .number(product.getNumber())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();

        return productResponseDto;
    }

    @Override
    public ProductResponseDto saveProduct(ProductDto productDto) {
        Product product = Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .stock(productDto.getStock())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Product savedProduct = productDAO.insertProduct(product);

        ProductResponseDto productResponseDto = ProductResponseDto.builder()
                .number(savedProduct.getNumber())
                .name(savedProduct.getName())
                .price(savedProduct.getPrice())
                .stock(savedProduct.getStock())
                .build();

        return productResponseDto;
    }

    @Override
    public ProductResponseDto changeProductName(Long number, String name) throws Exception {
        Product changedProduct = productDAO.updateProductName(number, name);

        ProductResponseDto productResponseDto = ProductResponseDto.builder()
                .number(changedProduct.getNumber())
                .name(changedProduct.getName())
                .price(changedProduct.getPrice())
                .stock(changedProduct.getStock())
                .build();

        return productResponseDto;
    }

    @Override
    public void deleteProduct(Long number) throws Exception {
        productDAO.deleteProduct(number);
    }
}
