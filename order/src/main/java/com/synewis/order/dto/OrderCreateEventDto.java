package com.synewis.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateEventDto {
    private String orderId;
    private String productId;
    private int quantity;
}
