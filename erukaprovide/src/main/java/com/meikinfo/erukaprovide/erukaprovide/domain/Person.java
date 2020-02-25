package com.meikinfo.erukaprovide.erukaprovide.domain;

import lombok.Data;

@Data
public class Person {
    private String id;

    private String name;

    private String age;

    public Person(String id, String name, String age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
