package com.bachar.e_commerce.controller;

import com.bachar.e_commerce.entity.Product;
import com.bachar.e_commerce.model.BrandResponse;
import com.bachar.e_commerce.model.ProductResponse;
import com.bachar.e_commerce.model.TypeResponse;
import com.bachar.e_commerce.service.BrandService;
import com.bachar.e_commerce.service.ProductService;
import com.bachar.e_commerce.service.TypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    private final TypeService typeService;
    private final BrandService brandService;

    public ProductController(ProductService productService, TypeService typeService, BrandService brandService) {
        this.productService = productService;
        this.typeService = typeService;
        this.brandService = brandService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") Integer productId) {
        ProductResponse productResponse = productService.getProductById(productId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts() {
        List<ProductResponse> productResponseList = productService.getProducts();
        return new ResponseEntity<>(productResponseList, HttpStatus.OK);
    }

    @GetMapping("/brands")
    public ResponseEntity<List<BrandResponse>> getBrands() {
        List<BrandResponse> brandResponseList = brandService.getAllBrands();
        return new ResponseEntity<>(brandResponseList, HttpStatus.OK);
    }

    @GetMapping("/types")
    public ResponseEntity<List<TypeResponse>> getTypes() {
        List<TypeResponse> typeResponseList = typeService.getAllTypes();
        return new ResponseEntity<>(typeResponseList, HttpStatus.OK);
    }
}
