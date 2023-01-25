package com.practice.productservice.service;

import com.practice.productservice.dto.ProductRequest;
import com.practice.productservice.dto.ProductResponse;
import com.practice.productservice.model.Product;
import com.practice.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    private static ProductResponse mapProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        Product createdProduct = productRepository.save(product);
        log.info("product save with id {}", product.getId());
        return mapProductResponse(createdProduct);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(ProductService::mapProductResponse
        ).collect(Collectors.toList());
    }
}
