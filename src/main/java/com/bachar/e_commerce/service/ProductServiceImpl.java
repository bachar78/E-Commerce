package com.bachar.e_commerce.service;

import com.bachar.e_commerce.entity.Product;
import com.bachar.e_commerce.exceptions.NotFoundException;
import com.bachar.e_commerce.model.ProductResponse;
import com.bachar.e_commerce.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;


    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<ProductResponse> getProducts(Pageable pageable, Integer brandId, Integer typeId, String keyword) {
        //Fetching from DB
        log.info("Fetching products !!!!");
        Specification<Product> spec = Specification.where(null);
        if (brandId != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("productBrand").get("id"), brandId));
        }
        if (typeId != null) {
            spec = spec.and(((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("productType").get("id"), typeId)));
        }

        if (keyword != null && !keyword.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + keyword + "%"));
        }
        log.info("Fetch all products from database");

        return productRepository.findAll(spec, pageable).map(this::convertToProductionResponse);
    }

    @Override
    public ProductResponse getProductById(Integer productId) {
        log.info("Get product by id: {}", productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException(String.format("Product with id %s not found", productId)));
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
