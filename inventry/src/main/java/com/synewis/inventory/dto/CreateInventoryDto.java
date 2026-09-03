package com.synewis.inventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateInventoryDto {
    @NotNull(message = "Product id is required")
    private String productId;

    @NotNull(message = "Warehouse id is required")
    private String warehouseId;

    @NotNull(message = "Product quantity is required")
    @Min(value = 10, message = "Minimum 10 item is required")
    private int quantityOnHand;
}
