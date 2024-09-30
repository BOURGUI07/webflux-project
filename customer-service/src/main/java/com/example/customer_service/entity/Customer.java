package com.example.customer_service.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name="customer")
@NoArgsConstructor
@Accessors(chain = true)
public class Customer {
    @Id
    private Integer id;
    private String name;
    private Integer balance;
}
