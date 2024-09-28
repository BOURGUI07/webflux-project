package com.example.webflux.playground.sec05.service;

import com.example.webflux.playground.sec05.dto.paginated.PaginatedCustomerResponse;
import com.example.webflux.playground.sec05.dto.request.CustomerRequest;
import com.example.webflux.playground.sec05.dto.response.CustomerResponse;
import com.example.webflux.playground.sec05.mapper.CustomerMapper;
import com.example.webflux.playground.sec05.repo.CustomerRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level= AccessLevel.PRIVATE)
public class CustomerService {
    CustomerRepo repo;
    CustomerMapper mapper;

    public Mono<CustomerResponse> findById(Integer id){
        return repo.findById(id)
                .map(mapper::toResponse);
    }


    public Flux<CustomerResponse> findAll(){
        return repo.findAll()
                .map(mapper::toResponse);
    }

    public Mono<CustomerResponse> save(Mono<CustomerRequest> request){
         return request.map(mapper::toEntity)
                 .flatMap(repo::save)
                 .map(mapper::toResponse);

    }


    public Mono<CustomerResponse> update(Integer id, Mono<CustomerRequest> mono){
        var entityMono = repo.findById(id);
        return Mono.zip(entityMono, mono)
                .flatMap(x->{
                    var request = x.getT2();
                    var entity = x.getT1().setName(request.name()).setEmail(request.email());
                    return repo.save(entity);
                }).map(mapper::toResponse);
    }

    public Mono<Boolean> delete(Integer id){
        return repo.deleteCustomerById(id);
    }

    public Mono<PaginatedCustomerResponse> findAllPaginated(int page, int size){
        return repo.findBy(PageRequest.of(page, size))
                .map(mapper::toResponse)
                .collectList()
                .zipWith(repo.findAll().count())
                .map(x-> new PaginatedCustomerResponse(x.getT1(),x.getT2()));
    }
}
