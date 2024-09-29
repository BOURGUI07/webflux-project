package com.example.webflux.playground.sec08.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Table(name="product")
public class Product {
    private Integer id;
    private String description;
    private Integer price;
}
