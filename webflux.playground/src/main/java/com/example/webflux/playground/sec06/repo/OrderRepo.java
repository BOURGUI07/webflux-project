package com.example.webflux.playground.sec06.repo;

import com.example.webflux.playground.sec06.dto.projections.OrderDetails;
import com.example.webflux.playground.sec06.entity.Order;
import com.example.webflux.playground.sec06.entity.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface OrderRepo extends ReactiveCrudRepository<Order, UUID> {
    @Query("""
        SELECT p.* 
        FROM product p JOIN customer_order o ON(p.id = o.product_id) 
        JOIN customer c ON c.id = o.customer_id 
        WHERE c.name= :name
        """)
    Flux<Product> findProductsOrderedByCustomer(String name);

    @Query("""
        SELECT o.order_id, c.name, p.description, o.amount, o.order_date
        FROM customer_order o JOIN customer c ON c.id = o.customer_id
        JOIN product p ON p.id = o.product_id
        WHERE p.description= :productName
        ORDER BY o.amount DESC
""")
    Flux<OrderDetails> findOrderDetailsByProductName(String productName);
}
