package com.bachar.e_commerce.entity.orderAggregate;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductItemOrder {
    private Integer productId;
    private String name;
    private String pictureUrl;
}
