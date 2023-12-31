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
    private Mono<ServerResponse> response406 = ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).build();

    public Mono<ServerResponse> getCustomers(ServerRequest request) {
        Flux<Customer> customerFlux = customerRepository.findAll();
        return ServerResponse.ok() //ServerResponse.BodyBuilder
                .contentType(APPLICATION_JSON) //ServerResponse.BodyBuilder
                .body(customerFlux, Customer.class);
    }

    public Mono<ServerResponse> getCustomer(ServerRequest request) {
        Long id = Long.parseLong(request.pathVariable("id"));
        return customerRepository.findById(id) //Mono<Customer>
                .flatMap(customer -> ServerResponse.ok() //ServerResponse.BodyBuilder
                        .contentType(APPLICATION_JSON) //ServerResponse.BodyBuilder
                        .body(BodyInserters.fromValue(customer)) //Mono<ServerResponse>
                ).switchIfEmpty(getError(id));
    }
    private Mono<ServerResponse> getError(Long id) {
        return Mono.error(new CustomAPIException("Customer Not Found with id " + id, HttpStatus.NOT_FOUND));
    }
    public Mono<ServerResponse> saveCustomer(ServerRequest request) {
        Mono<Customer> unSavedCustomerMono = request.bodyToMono(Customer.class);
        return unSavedCustomerMono.flatMap(customer ->
                customerRepository.save(customer)
                        .flatMap(savedCustomer ->
                                ServerResponse.accepted() //202 status code ServerResponse.BodyBuilder
                                        .contentType(APPLICATION_JSON) //ServerResponse.BodyBuilder
                                        .bodyValue(savedCustomer) //Mono<ServerResponse>
                        )
        ).switchIfEmpty(response406);
    }

    public Mono<ServerResponse> updateCustomer(ServerRequest request) {
        Long id = Long.parseLong(request.pathVariable("id"));
        Mono<Customer> unUpdatedCustomerMono = request.bodyToMono(Customer.class);

        Mono<Customer> updatedCustomerMono = unUpdatedCustomerMono.flatMap(customer ->
                customerRepository.findById(id)
                        .flatMap(existCustomer -> {
                            existCustomer.setFirstName(customer.getFirstName());
                            existCustomer.setLastName(customer.getLastName());
                            return customerRepository.save(existCustomer);
                        })
        );

        return updatedCustomerMono.flatMap(customer ->
                ServerResponse.accepted()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(customer)
        ).switchIfEmpty(getError(id));
    }
    public Mono<ServerResponse> deleteCustomer(ServerRequest request) {
        Long id = Long.parseLong(request.pathVariable("id"));
        return customerRepository.findById(id)
                .flatMap(existCustomer ->
                        ServerResponse.ok() //ServerResponse.BodyBuilder
                                .build(customerRepository.delete(existCustomer)))
                .switchIfEmpty(getError(id));
    }
}
