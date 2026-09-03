package com.synewis.products.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Document("Products")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    @Id
    private String id = UUID.randomUUID().toString();

    private String name;

    private String description;

    @Field("unit_price")
    private BigDecimal price;

    private Boolean forSale;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
