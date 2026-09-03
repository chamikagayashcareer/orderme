package com.synewis.inventory.controller;

import com.synewis.inventory.dto.InventoryResponseDto;
import com.synewis.inventory.dto.CreateInventoryDto;
import com.synewis.inventory.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.synewis.inventory.common.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventories")
public class InventoryController {
    @Autowired
    private InventoryService service;

    @PostMapping()
    public ResponseEntity<ApiResponse<InventoryResponseDto>> create(@Valid @RequestBody CreateInventoryDto dto){
        InventoryResponseDto response = service.create(dto);
        return ResponseEntity
                .status(201)
                .body(ApiResponse.success("Inventory Created",  response));
    }

    @GetMapping("get-by-product/{id}")
    public ResponseEntity<ApiResponse<InventoryResponseDto>> getByProduct(@PathVariable("id") String id){
        InventoryResponseDto response = service.getOneByProduct(id);
        return  ResponseEntity
                .status(200)
                .body(ApiResponse.success("Inventory fetched by product id", response));
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<InventoryResponseDto>> getOne(@PathVariable("id") String id){
        InventoryResponseDto response = service.getOne(id);
        return  ResponseEntity
                .status(200)
                .body(ApiResponse.success("Inventory Updated", response));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<InventoryResponseDto>>> get(){
        List<InventoryResponseDto> list = service.getAll();
        return ResponseEntity
                .status(200)
                .body(ApiResponse.success("Successfully fetched items", list));
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<InventoryResponseDto>> update(@PathVariable("id") String id, @RequestBody CreateInventoryDto dto){
        InventoryResponseDto response = service.update(id, dto);

        return ResponseEntity
                .status(200)
                .body(ApiResponse.success("Inventory updated", response));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable("id") String id){
        service.delete(id);

        return ResponseEntity
                .status(200)
                .body(ApiResponse.success("Inventory deleted", null));
    }
}
