package com.ktds.mywebflux;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class MyCustomer {
    private int id;
    private String name;
    private String email;
    private List<String> phoneNumbers;

    public MyCustomer(String name, String email) {
        this.name = name;
        this.email = email;
    }
}