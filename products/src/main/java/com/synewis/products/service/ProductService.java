package com.synewis.products.service;

import com.synewis.products.dto.CreateProductDto;
import com.synewis.products.dto.ProductResponseDto;
import com.synewis.products.entity.Product;
import com.synewis.products.repository.ProductRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public ProductResponseDto create(CreateProductDto dto){
        Product product = modelMapper.map(dto, Product.class);
        product.setForSale(true);
        product.setCreatedAt(Instant.now());
        product.setUpdatedAt(Instant.now());
        productRepo.save(product);

        return modelMapper.map(product, ProductResponseDto.class);
    }

    @Transactional(readOnly = true)
    public ProductResponseDto getOne(String id){
        Product product = productRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Product not found for id: " + id));

        return modelMapper.map(product, ProductResponseDto.class);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> getAll(){
        List<Product> productList = productRepo.findAll();
        return productList.stream().map((product)-> modelMapper.map(product, ProductResponseDto.class)).toList();
    }

    @Transactional
    public ProductResponseDto update(String id, CreateProductDto dto){
        Product product = productRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Product not found for id: " + id));

        modelMapper.map(dto, product);
        product.setUpdatedAt(Instant.now());

        productRepo.save(product);

        return modelMapper.map(product, ProductResponseDto.class);
    }

    @Transactional
    public void delete(String id){
        Product product = productRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Product not found for id: " + id));
        productRepo.delete(product);
    }

}
