package com.ktds.mywebflux;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class LambdaTest {

    List<MyCustomer> customers = List.of(
            new MyCustomer(101, "john", "john@gmail.com", Arrays.asList("397937955", "21654725")),
            new MyCustomer(102, "smith", "smith@gmail.com", Arrays.asList("89563865", "2487238947")),
            new MyCustomer(103, "peter", "peter@gmail.com", Arrays.asList("38946328654", "3286487236")),
            new MyCustomer(104, "kely", "kely@gmail.com", Arrays.asList("389246829364", "948609467"))
    );
    @Test
    void runnable() {
        //1. Anonymous Inner Class 형태로  Runnable을 구현하기
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Anonymous Inner Class");
            }
        });
        t1.start();
        //2. Lambda Expression
        Thread t2 = new Thread(() -> System.out.println("Lambda Expression"));
        t2.start();
    }
}
