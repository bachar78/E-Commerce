package com.bachar.e_commerce.service;

import com.bachar.e_commerce.model.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Page<ProductResponse> getProducts(Pageable pageable, Integer brandId, Integer typeId, String keyword);
    ProductResponse getProductById(Integer productId);
}
