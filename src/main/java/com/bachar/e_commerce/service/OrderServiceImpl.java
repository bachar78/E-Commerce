package com.bachar.e_commerce.service;

import com.bachar.e_commerce.Mapper.OrderMapper;
import com.bachar.e_commerce.entity.orderAggregate.Order;
import com.bachar.e_commerce.model.OrderDTO;
import com.bachar.e_commerce.model.OrderResponse;
import com.bachar.e_commerce.repository.BrandRepository;
import com.bachar.e_commerce.repository.OrderRepository;
import com.bachar.e_commerce.repository.TypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BrandRepository brandRepository;
    private final TypeRepository typeRepository;
    private final OrderMapper orderMapper;
    private final BasketService basketService;

    public OrderServiceImpl(OrderRepository orderRepository, BrandRepository brandRepository, TypeRepository typeRepository, OrderMapper orderMapper, BasketService basketService) {
        this.orderRepository = orderRepository;
        this.brandRepository = brandRepository;
        this.typeRepository = typeRepository;
        this.orderMapper = orderMapper;
        this.basketService = basketService;
    }


    @Override
    public OrderResponse getOrderById(Integer id) {
         Optional<Order> optionalOrder = orderRepository.findById(id);
         return optionalOrder.map(orderMapper::orderToOrderResponse).orElse(null);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(orderMapper::orderToOrderResponse).toList();
    }

    @Override
    public Page<OrderResponse> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(orderMapper::orderToOrderResponse);
    }

    @Override
    public Integer createOrder(OrderDTO orderDTO) {
        return 0;
    }

    @Override
    public void deleteOrder(Integer orderId) {
        orderRepository.deleteById(orderId);
    }
}
