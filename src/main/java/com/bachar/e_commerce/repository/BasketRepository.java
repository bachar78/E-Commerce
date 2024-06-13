package com.bachar.e_commerce.repository;

import com.bachar.e_commerce.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BasketRepository extends CrudRepository<Basket, String> {
}
