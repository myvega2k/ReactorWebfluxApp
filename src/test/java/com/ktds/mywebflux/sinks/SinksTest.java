package com.ktds.mywebflux.sinks;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

public class SinksTest {

    @Test
    void sinkOne() {
        Sinks.One<Object> sink = Sinks.one();
        Mono<Object> mono = sink.asMono();

        mono.subscribe(msg -> System.out.println("Sam " + msg));
        mono.subscribe(msg -> System.out.println("Mike " + msg));
        sink.tryEmitValue("Hello");
        sink.tryEmitValue("Hi");
    }
}
