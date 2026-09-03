package com.synewis.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InventoryResponseDto {
    private String id;
    private String productId;
    private String warehouseId;
    private int quantityOnHand;
}
