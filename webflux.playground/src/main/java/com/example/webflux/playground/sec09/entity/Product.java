package com.example.webflux.playground.sec09.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Table(name="product")
public class Product {
    @Id
    private Integer id;
    private String description;
    private Integer price;
}
