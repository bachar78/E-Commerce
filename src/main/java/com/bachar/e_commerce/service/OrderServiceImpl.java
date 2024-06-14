package com.bachar.e_commerce.service;
import com.bachar.e_commerce.Mapper.OrderMapper;
import com.bachar.e_commerce.entity.orderAggregate.Order;
import com.bachar.e_commerce.entity.orderAggregate.OrderItem;
import com.bachar.e_commerce.entity.orderAggregate.ProductItemOrder;
import com.bachar.e_commerce.exceptions.NotFoundException;
import com.bachar.e_commerce.model.BasketItemResponse;
import com.bachar.e_commerce.model.BasketResponse;
import com.bachar.e_commerce.model.OrderDTO;
import com.bachar.e_commerce.model.OrderResponse;
import com.bachar.e_commerce.repository.BrandRepository;
import com.bachar.e_commerce.repository.OrderRepository;
import com.bachar.e_commerce.repository.TypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
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
        //Fetching basket details
        BasketResponse basketResponse = basketService.getBasketById(orderDTO.getBasketId());
        if (basketResponse == null) {
            log.error("Basket not found with id {}", orderDTO.getBasketId());
            throw new NotFoundException("Basket not found with id " + orderDTO.getBasketId());
        }
        //Map basket item to order items
        List<OrderItem> orderItems = basketResponse.getItems().stream().map(this::mapBasketItemToOrderItem).toList();
        //Calculate subtotal
        double subTotal = basketResponse.getItems().stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
        Order order = orderMapper.orderResponseToOrder(orderDTO);
        order.setSubTotal(subTotal);
        order.setOrderItems(orderItems);
        //save the order
        Order savedOrder = orderRepository.save(order);
        basketService.deleteBasketById(basketResponse.getId());
        //return the response
        return savedOrder.getId();
    }

    private OrderItem mapBasketItemToOrderItem(BasketItemResponse basketItemResponse) {
        if (basketItemResponse != null) {
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(basketItemResponse.getQuantity());
            orderItem.setPrice(basketItemResponse.getPrice());
            orderItem.setProductItemOrder(mapBasketItemToProduct(basketItemResponse));
            return orderItem;
        } else {
            return null;
        }
    }

    private ProductItemOrder mapBasketItemToProduct(BasketItemResponse basketItemResponse) {
        if (basketItemResponse != null) {
            return ProductItemOrder.builder()
                    .productId(basketItemResponse.getId())
                    .name(basketItemResponse.getName())
                    .pictureUrl(basketItemResponse.getPictureUrl()).build();
        } else {
            return null;
        }
    }

    @Override
    public void deleteOrder(Integer orderId) {
        orderRepository.deleteById(orderId);
    }
}
