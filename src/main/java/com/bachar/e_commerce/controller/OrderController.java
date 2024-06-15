package com.bachar.e_commerce.controller;

import com.bachar.e_commerce.model.OrderDTO;
import com.bachar.e_commerce.model.OrderResponse;
import com.bachar.e_commerce.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable("orderId") Integer orderId) {
        OrderResponse order = orderService.getOrderById(orderId);
        return ResponseEntity.ok().body(order);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<OrderResponse> orders = orderService.getAllOrders();
        return ResponseEntity.ok().body(orders);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<OrderResponse>> getPageAllOrders(Pageable pageable) {
        Page<OrderResponse> pagedOrders = orderService.getAllOrders(pageable);
        return ResponseEntity.ok().body(pagedOrders);
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        Integer orderId = orderService.createOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderId);
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable("orderId") Integer orderId) {
        orderService.deleteOrder(orderId);
    }

}

