package com.synewis.products.controller;

import com.synewis.products.common.ApiResponse;
import com.synewis.products.dto.CreateProductDto;
import com.synewis.products.dto.ProductResponseDto;
import com.synewis.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    @Autowired
    private ProductService service;

    @PostMapping()
    public ResponseEntity<ApiResponse<ProductResponseDto>> create(@RequestBody CreateProductDto dto){
        ProductResponseDto response = service.create(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Product Created", response));
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> update(@PathVariable("id") String id, @RequestBody CreateProductDto dto){
        ProductResponseDto response = service.update(id, dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("Product Updated", response));
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> getOne(@PathVariable("id") String id){
        ProductResponseDto response = service.getOne(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("Product Successfully fetched", response));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getAll(){
        List<ProductResponseDto> response = service.getAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("All Products Successfully fetched", response));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> delete(@PathVariable("id") String id){
        service.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("Product Successfully deleted", null));
    }
}
