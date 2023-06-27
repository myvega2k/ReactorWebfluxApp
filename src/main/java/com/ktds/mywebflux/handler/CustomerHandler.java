package com.ktds.mywebflux.handler;

import com.ktds.mywebflux.entity.Customer;
import com.ktds.mywebflux.exception.CustomAPIException;
import com.ktds.mywebflux.repository.R2CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
public class CustomerHandler {
    private final R2CustomerRepository customerRepository;

    public Mono<ServerResponse> getCustomers(ServerRequest request) {
        Flux<Customer> customerFlux = customerRepository.findAll();
        return ServerResponse.ok() //ServerResponse.BodyBuilder
                .contentType(APPLICATION_JSON) //ServerResponse.BodyBuilder
                .body(customerFlux, Customer.class);
    }

    public Mono<ServerResponse> getCustomer(ServerRequest request) {
        Long id = Long.parseLong(request.pathVariable("id"));
        return customerRepository.findById(id)
                .flatMap(customer -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .body(BodyInserters.fromValue(customer))
                ).switchIfEmpty(getError(id));
    }
    private Mono<ServerResponse> getError(Long id) {
        return Mono.error(new CustomAPIException("Customer Not Found with id " + id, HttpStatus.NOT_FOUND));
    }
}
