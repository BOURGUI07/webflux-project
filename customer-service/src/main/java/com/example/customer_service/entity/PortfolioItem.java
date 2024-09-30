package com.example.customer_service.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@Table(name = "portfolio_item")
@Accessors(chain = true)
public class PortfolioItem {
    private Integer id;
    private Integer customerId;
    private Ticker ticker;
    private Integer quantity;
}
