package com.synewis.inventory.repository;

import com.synewis.inventory.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface InventoryRepo extends JpaRepository<Inventory, String> {

    @Query(value = "SELECT * FROM inventory WHERE product_id = ?1", nativeQuery = true)
    Inventory getInventoryByProductId(String id);
}
