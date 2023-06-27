package com.ktds.mywebflux.router;

import com.ktds.mywebflux.entity.Customer;
import com.ktds.mywebflux.handler.CustomerHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class CustomerRouterFunction {
    @Bean
    public RouterFunction<ServerResponse> routerFunction(CustomerHandler customerHandler) {
        //public Mono<ServerResponse> getCustomers(ServerRequest request) {
        //HandlerFunction 의 추상메서드 Mono<T> handle(ServerRequest request);
        return RouterFunctions.route(GET("/route/r2customers"),customerHandler::getCustomers);
    }
//    public RouterFunction<ServerResponse> routerFunction() {
//        return RouterFunctions.route(GET("/route/r2customers"), new HandlerFunction<ServerResponse>() {
//            @Override
//            public Mono<ServerResponse> handle(ServerRequest request) {
//                Flux<Customer> customerFlux = customerRepository.findAll();
//                return ServerResponse.ok() //ServerResponse.BodyBuilder
//                        .contentType(MediaType.APPLICATION_JSON) //ServerResponse.BodyBuilder
//                        .body(customerFlux, Customer.class);
//            }
//        });
//    }


}
