package com.synewis.order.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String productId;

    private String customerId;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private int quantity;

    private BigDecimal totalAmount;

    private String shippingAddress;
}
