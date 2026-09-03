package com.synewis.products.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductResponseDto {
    private String id;

    private String name;

    private String description;

    private BigDecimal price;

    private Boolean forSale;

    private Instant createdAt;

    private Instant updatedAt;
}
