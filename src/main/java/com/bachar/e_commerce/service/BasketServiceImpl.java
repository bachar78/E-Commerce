package com.bachar.e_commerce.service;

import com.bachar.e_commerce.entity.Basket;
import com.bachar.e_commerce.entity.BasketItem;
import com.bachar.e_commerce.exceptions.NotFoundException;
import com.bachar.e_commerce.model.BasketItemResponse;
import com.bachar.e_commerce.model.BasketResponse;
import com.bachar.e_commerce.repository.BasketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;

    public BasketServiceImpl(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    @Override
    public List<BasketResponse> getAllBaskets() {
        log.info("Fetching all baskets");
        List<Basket> basketList = (List<Basket>) basketRepository.findAll();
        //Use stream operator to map with response
        List<BasketResponse> basketResponseList = basketList.stream().map(this::convertToBasketResponse).toList();
        log.info("Fetched {} baskets", basketList.size());
        return basketResponseList;
    }

    @Override
    public BasketResponse getBasketById(String id) {
        log.info("Fetching basket by id {}", id);
        Basket basket = basketRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Product with id %s not found", id)));
        log.info("Fetched basket by id {}", basket.getId());
        return convertToBasketResponse(basket);
    }

    @Override
    public void deleteBasketById(String id) {
        log.info("Deleting basket by id {}", id);
        basketRepository.deleteById(id);
        log.info("Deleted basket by {}", id);
    }

    @Override
    public BasketResponse createBasket(Basket basket) {
        log.info("Creating basket {}", basket);
        Basket savedBasket = basketRepository.save(basket);
        log.info("Created basket with id {}", savedBasket.getId());
        return convertToBasketResponse(savedBasket);
    }

    private BasketResponse convertToBasketResponse(Basket basket) {
        if (basket == null) {
            return null;
        }
        List<BasketItemResponse> itemResponseList = basket.getItems().stream().map(this::convertToBasketItemResponse).toList();
        return BasketResponse.builder().id(basket.getId()).items(itemResponseList).build();
    }

    private BasketItemResponse convertToBasketItemResponse(BasketItem basketItem) {
        return BasketItemResponse.builder()
                .id(basketItem.getId())
                .name(basketItem.getName())
                .description(basketItem.getDescription())
                .price(basketItem.getPrice())
                .pictureUrl(basketItem.getPictureUrl())
                .productBrand(basketItem.getProductBrand())
                .productType(basketItem.getProductType())
                .quantity(basketItem.getQuantity())
                .build();
    }
}
