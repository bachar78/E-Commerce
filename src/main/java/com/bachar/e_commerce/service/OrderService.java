package com.bachar.e_commerce.service;

import com.bachar.e_commerce.model.OrderDTO;
import com.bachar.e_commerce.model.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    OrderResponse getOrderById(Integer id);
    List<OrderResponse> getAllOrders();
    Page<OrderResponse> getAllOrders(Pageable pageable);
    Integer createOrder(OrderDTO orderDTO);
    void deleteOrder(Integer id);
}
