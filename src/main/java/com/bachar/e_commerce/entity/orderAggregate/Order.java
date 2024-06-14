package com.bachar.e_commerce.entity.orderAggregate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "Basket_Id")
    private String basketId;
    @Embedded
    private ShippingAddress shippingAddress;
    @Column(name = "Order_date")
    private LocalDateTime orderDate = LocalDateTime.now();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderItem> orderItems;
    @Column(name = "Sub_Total")
    private Double subTotal;
    @Column(name = "Delivery_Fee")
    private Long deliveryFee;
    @Enumerated(EnumType.STRING)
    @Column(name = "Order_Status")
    private OrderStatus orderStatus = OrderStatus.Pending;

    public Double getSubTotal() {
        return getSubTotal()+getDeliveryFee();
    }




}
