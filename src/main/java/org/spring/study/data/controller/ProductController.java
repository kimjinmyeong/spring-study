package org.spring.study.data.controller;

import jakarta.validation.Valid;
import org.spring.study.data.dto.ChangeProductNameDto;
import org.spring.study.data.dto.ProductDto;
import org.spring.study.data.dto.ProductResponseDto;
import org.spring.study.data.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ProductResponseDto> getProduct(Long number) {
        ProductResponseDto productResponseDto = productService.getProduct(number);

        return ResponseEntity.status(HttpStatus.OK).body(productResponseDto);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@Validated @RequestBody ProductDto productDto) {
        ProductResponseDto productResponseDto = productService.saveProduct(productDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(productResponseDto);
    }

    @PutMapping
    public ResponseEntity<ProductResponseDto> changeProductName(
            @Validated @RequestBody ChangeProductNameDto changeProductNameDto) throws Exception {
        ProductResponseDto productResponseDto = productService.changeProductName(changeProductNameDto.getNumber(), changeProductNameDto.getName());
        return ResponseEntity.status(HttpStatus.OK).body(productResponseDto);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProduct(Long number) throws Exception {
        productService.deleteProduct(number);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
