package com.bachar.e_commerce.service;

import com.bachar.e_commerce.model.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getProducts();
    ProductResponse getProductById(Integer productId);
}
