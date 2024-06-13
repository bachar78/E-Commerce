package com.bachar.e_commerce.service;

import com.bachar.e_commerce.entity.Product;
import com.bachar.e_commerce.model.ProductResponse;
import com.bachar.e_commerce.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;


    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<ProductResponse> getProducts(Pageable pageable) {
        log.info("Fetching products !!!!");
        Page<Product> productsPage = productRepository.findAll(pageable);
        Page<ProductResponse> productResponsePage = productsPage
                .map(this::convertToProductionResponse);
        log.info("Fetch all products from database");
        return productResponsePage;
    }

    @Override
    public ProductResponse getProductById(Integer productId) {
        log.info("Get product by id: {}", productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException(String.format("Product with id %s not found", productId)));
        log.info("Product by id: {} fetched", productId);
        //now convert the product to product response
        return convertToProductionResponse(product);
    }

    private ProductResponse convertToProductionResponse(Product product) {
        return ProductResponse.builder().id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .pictureUrl(product.getPictureUrl())
                .productBrand(product.getProductBrand().getName())
                .productType(product.getProductType().getName())
                .build();
    }
}
