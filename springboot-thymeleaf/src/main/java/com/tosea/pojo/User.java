package com.tosea.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    String name;
    int age;
    String role;
    User friend;// 对象类型属性

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}