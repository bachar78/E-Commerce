package com.bachar.e_commerce.model;

import com.bachar.e_commerce.entity.Order;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class OrderMapper implements Function<Order, OrderResponse> {
    @Override
    public OrderResponse apply(Order order) {
        return new OrderResponse(

        );
    }
}
