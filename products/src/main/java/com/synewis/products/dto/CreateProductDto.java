package com.synewis.products.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateProductDto {
    @NotNull(message = "Name can not be empty")
    private String name;

    private String description;

    @NotNull(message = "Category can not be empty")
    private String category;

    @NotNull(message = "Unit Price can not be empty")
    @Min(value = 0, message = "Unite price need to positive value")
    private double price;
}
