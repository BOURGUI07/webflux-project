package com.example.webflux.playground.sec08.mapper;

import com.example.webflux.playground.sec08.dto.ProductRequest;
import com.example.webflux.playground.sec08.dto.ProductResponse;
import com.example.webflux.playground.sec08.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product toEntity(ProductRequest request){
        return new Product().setDescription(request.description()).setPrice(request.price());
    }

    public ProductResponse toResponse(Product product){
        return new ProductResponse(product.getId(), product.getDescription(), product.getPrice());
    }
}
