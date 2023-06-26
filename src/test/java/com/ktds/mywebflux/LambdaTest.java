package com.ktds.mywebflux;

import org.junit.jupiter.api.Test;

public class LambdaTest {
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
