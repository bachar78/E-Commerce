package com.bachar.e_commerce.Mapper;

import com.bachar.e_commerce.entity.orderAggregate.Order;
import com.bachar.e_commerce.model.OrderDTO;
import com.bachar.e_commerce.model.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

import java.util.List;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "basketId", target = "basketId")
    @Mapping(source = "shippingAddress", target = "shippingAddress")
    @Mapping(source = "subTotal", target = "subTotal")
    @Mapping(source = "deliveryFee", target = "deliveryFee")
    @Mapping(target = "total", expression = "java(order.getSubTotal() + order.getDeliveryFee())")
    @Mapping(target = "orderDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "orderStatus", constant = "Pending")
    OrderResponse orderToOrderResponse(Order order);

    @Mapping(target = "orderDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "orderStatus", constant = "Pending")
    Order orderResponseToOrder(OrderDTO orderDTO);

    List<OrderDTO> ordersToOrderResponses(List<Order> orders);

    void updateOrderFromOrderResponse(OrderDTO orderDTO, @MappingTarget Order order);
}
