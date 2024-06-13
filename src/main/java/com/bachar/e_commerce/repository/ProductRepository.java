package com.bachar.e_commerce.repository;

import com.bachar.e_commerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findAll(Specification<Product> spec, Pageable pageable);
    Specification<Product> searchByNameContaining(String keyword);
    Specification<Product> findByProductBrandId(int brandId);
    Specification<Product> findByProductTypeId(int typeId);
    Specification<Product> findByProductBrandIdAndProductTypeId(int brandId, int typeId);
}
