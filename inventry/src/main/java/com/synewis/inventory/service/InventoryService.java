package com.synewis.inventory.service;

import com.synewis.inventory.common.ApiResponse;
import com.synewis.inventory.dto.InventoryResponseDto;
import com.synewis.inventory.dto.CreateInventoryDto;
import com.synewis.inventory.entity.Inventory;
import com.synewis.inventory.repository.InventoryRepo;
import com.synewis.products.dto.ProductResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Slf4j
@Service
public class InventoryService {
    @Autowired
    private InventoryRepo inventoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private WebClient productClient;

    @Transactional
    public InventoryResponseDto create(CreateInventoryDto dto){
            Inventory existingInventory = inventoryRepo.getInventoryByProductId(dto.getProductId());
             if(existingInventory != null) throw new RuntimeException("Inventory exist for product Id: " + dto.getProductId());

        ApiResponse<ProductResponseDto> productResponse = productClient.get()
                .uri(uriBuilder -> uriBuilder.path("/products/{itemId}").build(dto.getProductId()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<ProductResponseDto>>() {})
                .block();

            if(productResponse == null) {
                log.info(String.format("Failed communication products path: /products/%s", dto.getProductId()));
                throw new RuntimeException("Failed to communicate products");
            }

            if (productResponse.data() == null) throw new RuntimeException(productResponse.message());

            Inventory newInventory = inventoryRepo.save(modelMapper.map(dto, Inventory.class));
            return modelMapper.map(newInventory, InventoryResponseDto.class);
    }

    @Transactional
    public void reserve(int quantity, String productId){
        Inventory inventory = inventoryRepo.getInventoryByProductId(productId);

        inventory.setQuantity_reserved(inventory.getQuantity_reserved()+quantity);
        inventoryRepo.save(inventory);
    }

    @Transactional(readOnly = true)
    public InventoryResponseDto getOne(String id){
        Inventory inventory = inventoryRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Inventory not found for id: " + id));
        return modelMapper.map(inventory, InventoryResponseDto.class);
    }

    @Transactional(readOnly = true)
    public InventoryResponseDto getOneByProduct(String id){
        Inventory inventory = inventoryRepo.getInventoryByProductId(id);

        if(inventory == null) throw new RuntimeException("Inventory not found for productid: " + id);

        return modelMapper.map(inventory, InventoryResponseDto.class);
    }

    @Transactional(readOnly = true)
    public List<InventoryResponseDto> getAll(){
        List<Inventory> inventories = inventoryRepo.findAll();

       return inventories.stream().map((inventory -> modelMapper.map(inventory, InventoryResponseDto.class))).toList();
    }

    @Transactional
    public InventoryResponseDto update(String id, CreateInventoryDto newDto){
        Inventory inventory = inventoryRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Inventory not found for id: " + id));

        modelMapper.map(newDto, inventory);
        Inventory updateInventory = inventoryRepo.save(inventory);

        return modelMapper.map(updateInventory, InventoryResponseDto.class);
    }

    @Transactional
    public void delete(String id){
        Inventory inventory = inventoryRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Inventory not found for id: " + id));
        inventoryRepo.delete(inventory);
    }
}
