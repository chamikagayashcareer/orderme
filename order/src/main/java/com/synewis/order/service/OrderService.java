package com.synewis.order.service;

import com.synewis.inventory.dto.InventoryResponseDto;
import com.synewis.order.common.ApiResponse;
import com.synewis.order.dto.OrderCreateDto;
import com.synewis.order.dto.OrderCreateEventDto;
import com.synewis.order.dto.OrderResponseDto;
import com.synewis.order.entity.Order;
import com.synewis.order.entity.OrderStatus;
import com.synewis.order.kafka.OrderProducer;
import com.synewis.order.repository.OrderRepo;
import com.synewis.products.dto.ProductResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderProducer orderProducer;

    @Autowired
    private WebClient productClient;

    @Autowired
    private WebClient inventoryClient;

    @Transactional()
    public OrderResponseDto create(OrderCreateDto dto){
        ApiResponse<ProductResponseDto> productResponse = productClient.get()
                .uri(uriBuilder -> uriBuilder.path("/products/{itemId}").build(dto.getProductId()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<ProductResponseDto>>() {})
                .block();


//        ResponseEntity<ApiResponse<ProductResponseDto>> productResponseEntity = restTemplate.exchange(
//                productUrl + "/products/{itemId}",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<ApiResponse<ProductResponseDto>>() {},
//                dto.getProductId()
//        );

        if(productResponse == null) {
            log.info(String.format("Failed communication products path: /products/%s", dto.getProductId()));
            throw new RuntimeException("Failed to communicate products");
        }

        if (productResponse.data() == null) throw new RuntimeException(productResponse.message());

        if(!productResponse.data().getForSale()) throw new RuntimeException("This product is not for sale");

        ApiResponse<InventoryResponseDto> inventoryResponse = inventoryClient.get()
                .uri(uriBuilder -> uriBuilder.path("/inventories/get-by-product/{itemId}").build(dto.getProductId()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<InventoryResponseDto>>() {})
                .block();


//        ResponseEntity<ApiResponse<InventoryResponseDto>> inventoryResponseEntity = restTemplate.exchange(
//                inventoryUrl + "/inventories/get-by-product/{itemId}",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<ApiResponse<InventoryResponseDto>>() {},
//                dto.getProductId()
//        );

        if(inventoryResponse == null) {
            log.info(String.format("Failed communication Inventory path: /inventories/get-by-product/%s", dto.getProductId()));
            throw new RuntimeException("Failed to communicate Inventory");
        }

        if (inventoryResponse.data() == null) throw new RuntimeException(productResponse.message());

        if(!(inventoryResponse.data().getQuantityOnHand() > dto.getQuantity())) throw new RuntimeException("Product quantity is not enough");

        Order order = modelMapper.map(dto, Order.class);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setTotalAmount(productResponse.data().getPrice().multiply(BigDecimal.valueOf(order.getQuantity())));
        orderRepo.save(order);

        orderProducer.sendOrderCreate(new OrderCreateEventDto(order.getId(), order.getProductId(),order.getQuantity()));

        return modelMapper.map(order, OrderResponseDto.class);
    }

    @Transactional(readOnly = true)
    public OrderResponseDto getOne(String id){
        Order order = orderRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Order not found for id: "+ id));

        return modelMapper.map(order, OrderResponseDto.class);
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDto> getAll(){
        List<Order> orders = orderRepo.findAll();
        return orders.stream().map((order -> modelMapper.map(order, OrderResponseDto.class))).toList();
    }

    @Transactional
    public OrderResponseDto update(String id, OrderCreateDto dto){
        Order order = orderRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Order not found for id: "+ id));

        if((order.getQuantity() != dto.getQuantity()) && (order.getOrderStatus() != OrderStatus.PENDING))
            throw new RuntimeException("Quantity cannot be change, after order is confirmed");

        Order updatedOrder = orderRepo.save(order);
        return modelMapper.map(updatedOrder, OrderResponseDto.class);
    }

    @Transactional
    public void delete(String id){
        Order order = orderRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Order not found for id: "+ id));

        orderRepo.delete(order);
    }
}
