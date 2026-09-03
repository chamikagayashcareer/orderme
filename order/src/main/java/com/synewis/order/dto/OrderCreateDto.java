package com.synewis.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderCreateDto {
    @NotNull(message = "Customer id cannot be empty")
    private String customerId;

    @NotNull(message = "Product id cannot be empty")
    private String productId;

    @NotNull(message = "Quantity id cannot be empty")
    @Min(value = 1, message = "Minimum quantity not enough")
    private int quantity;

    @NotNull(message = "Shipping Address id cannot be empty")
    private String shippingAddress;
}
