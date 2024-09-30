package com.example.customer_service.service;

import com.example.customer_service.dto.CustomerInformation;
import com.example.customer_service.dto.Holding;
import com.example.customer_service.exceptions.ApplicationExceptions;
import com.example.customer_service.repo.CustomerRepo;
import com.example.customer_service.repo.PortfolioRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepo repo;
    private final PortfolioRepo portfolioRepo;

    public Mono<CustomerInformation> getCustomerInformation(Integer customerId) {
        return portfolioRepo.findByCustomerId(customerId)
                .map(x->new Holding(x.getTicker(),x.getQuantity()))
                .collectList()
                .zipWith(
                        repo.findById(customerId)
                                .switchIfEmpty(ApplicationExceptions.customerNoFoundException(customerId))
                )
                .map(x->{
                    var holdings = x.getT1();
                    var customer = x.getT2();
                    return new CustomerInformation(
                      customerId,customer.getName(),customer.getBalance(),holdings
                    );
                });

    }


}
