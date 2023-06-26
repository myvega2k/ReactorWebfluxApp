package com.ktds.mywebflux;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class LambdaTest {
    //Immutable List
    List<MyCustomer> customers = List.of(
            new MyCustomer(101, "john", "john@gmail.com", Arrays.asList("397937955", "21654725")),
            new MyCustomer(102, "smith", "smith@gmail.com", Arrays.asList("89563865", "2487238947")),
            new MyCustomer(103, "peter", "peter@gmail.com", Arrays.asList("38946328654", "3286487236")),
            new MyCustomer(104, "kely", "kely@gmail.com", Arrays.asList("389246829364", "948609467"))
    );

    /**
     * 함수형 인터페이스 (java.util.function) 종류
     * Consumer => void accept(T t)  입력만 있음
     * Supplier => T get()      출력만 있음
     * Predicate => boolean test(T t)  boolean 리턴
     * Function => R apply(T t) 입력과 출력 모두 있음
     * UnaryOperator = > Function(T,T) 와 동일
     * BinaryOperator => Function(T,T,T) 와 동일
     */

    @Test
    void emailList() {
        //MyCustomer의 email 주소만 꺼내서 List<String>을 반환하기
        //ctrl + alt + v
        List<String> emailList = customers.stream() //Stream<MyCustomer>
                .map(customer -> customer.getEmail()) //Stream<String>
                .collect(Collectors.toList());
       //emailList.forEach(email -> System.out.println(email));
        emailList.forEach(System.out::println);
    }
    @Test
    void consumer() {
        //void accept(T t)
        customers.forEach(new Consumer<MyCustomer>() {
            @Override
            public void accept(MyCustomer myCustomer) {
                System.out.println(myCustomer);
            }
        });
        //2. Lambda Expression
        customers.forEach(customer -> System.out.println(customer));
        System.out.println("===> Method Reference");
        //3. Method Reference
        customers.forEach(System.out::println);
    }

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
