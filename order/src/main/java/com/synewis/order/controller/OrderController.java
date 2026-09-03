package com.synewis.order.controller;

import com.synewis.order.common.ApiResponse;
import com.synewis.order.dto.OrderCreateDto;
import com.synewis.order.dto.OrderResponseDto;
import com.synewis.order.service.OrderService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {
    @Autowired
    private OrderService service;

    @PostMapping()
    public ResponseEntity<ApiResponse<OrderResponseDto>> create(@RequestBody OrderCreateDto dto){
        OrderResponseDto response = service.create(dto);
        return ResponseEntity
                .status(201)
                .body(ApiResponse.success("Order Created", response));
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> update(@PathVariable("id") String id, @RequestBody OrderCreateDto dto) throws BadRequestException {
        OrderResponseDto response = service.update(id, dto);

        return ResponseEntity
                .status(200)
                .body(ApiResponse.success("Order updated", response));
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> getOne(@PathVariable("id") String id){
        OrderResponseDto response = service.getOne(id);

        return ResponseEntity
                .status(200)
                .body(ApiResponse.success("Order successfully fetched", response));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<OrderResponseDto>>> getAll(){
        List<OrderResponseDto> list =  service.getAll();
        return ResponseEntity
                .status(200)
                .body(ApiResponse.success("Orders successfully fetched", list));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> delete(@PathVariable("id") String id){
        service.delete(id);
        return ResponseEntity
                .status(200)
                .body(ApiResponse.success("Successfully deleted", null));
    }
}
