package com.bachar.e_commerce.service;

import com.bachar.e_commerce.entity.Basket;
import com.bachar.e_commerce.model.BasketResponse;

import java.util.List;

public interface BasketService {
    List<BasketResponse> getAllBaskets();
    BasketResponse getBasketById(String id);
    void deleteBasketById(String id);
    BasketResponse createBasket(Basket basket);
}
