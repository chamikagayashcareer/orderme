package com.synewis.inventory.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String productId;
    private String warehouseId;
    private int quantityOnHand;
    private int quantity_reserved;
}
