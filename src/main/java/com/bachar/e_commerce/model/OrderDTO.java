package com.bachar.e_commerce.model;

import com.bachar.e_commerce.entity.orderAggregate.ShippingAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private String basketId;
    private ShippingAddress shippingAddress;
    private Long subTotal;
    private Long deliveryFee;
    private Long shippingCost;
    private LocalDateTime orderDate;
}
