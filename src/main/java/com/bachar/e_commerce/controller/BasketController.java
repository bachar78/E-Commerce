package com.bachar.e_commerce.controller;


import com.bachar.e_commerce.entity.Basket;
import com.bachar.e_commerce.entity.BasketItem;
import com.bachar.e_commerce.model.BasketItemResponse;
import com.bachar.e_commerce.model.BasketResponse;
import com.bachar.e_commerce.service.BasketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/baskets")
public class BasketController {
    private final BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }
    @GetMapping
    public List<BasketResponse> getBaskets() {
        return basketService.getAllBaskets();
    }

    @GetMapping("/{basketId}")
    public BasketResponse getBasket(@PathVariable("basketId") String basketId) {
        return basketService.getBasketById(basketId);
    }

    @DeleteMapping("/{basketId}")
    public void deleteBasket(@PathVariable("basketId") String basketId) {
        basketService.deleteBasketById(basketId);
    }

    @PostMapping
    public ResponseEntity<BasketResponse> createBasket(@RequestBody BasketResponse basketResponse) {
        //convert BasketResponse to Basket Entity
        Basket basket = convertToBasketEntity(basketResponse);
        BasketResponse savedBasketResponse = basketService.createBasket(basket);
        return new ResponseEntity<>(savedBasketResponse, HttpStatus.CREATED);
    }

    private Basket convertToBasketEntity(BasketResponse basketResponse) {
        Basket basket = new Basket();
        basket.setId(basketResponse.getId());
        basket.setItems(mapBasketItemsResponseToEntity(basketResponse.getItems()));
        return basket;
    }

    private List<BasketItem> mapBasketItemsResponseToEntity(List<BasketItemResponse> items) {
        return items.stream().map(this::mapBasketItemResponseToEntity).toList();
    }

    private BasketItem mapBasketItemResponseToEntity(BasketItemResponse itemResponse) {
        BasketItem basketItem = new BasketItem();
        basketItem.setId(itemResponse.getId());
        basketItem.setName(itemResponse.getName());
        basketItem.setDescription(itemResponse.getDescription());
        basketItem.setPrice(itemResponse.getPrice());
        basketItem.setPictureUrl(itemResponse.getPictureUrl());
        basketItem.setProductBrand(itemResponse.getProductBrand());
        basketItem.setProductType(itemResponse.getProductType());
        basketItem.setQuantity(itemResponse.getQuantity());
        return basketItem;
    }
}
