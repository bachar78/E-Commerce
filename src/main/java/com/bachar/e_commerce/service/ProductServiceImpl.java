package com.bachar.e_commerce.service;

import com.bachar.e_commerce.entity.Product;
import com.bachar.e_commerce.model.ProductResponse;
import com.bachar.e_commerce.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;


    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductResponse> getProducts() {
        return List.of();
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
