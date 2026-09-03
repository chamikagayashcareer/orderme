package com.synewis.order.dto;

import com.synewis.order.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderResponseDto {
    private String id;
    private String productId;
    private String customerId;
    private OrderStatus orderStatus;
    private int quantity;
    private float totalAmount;
    private String shippingAddress;
}
