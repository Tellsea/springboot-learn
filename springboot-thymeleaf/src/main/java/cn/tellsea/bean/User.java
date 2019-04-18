package cn.tellsea.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String name;
    private Integer age;
    private String role;
    private User friend;// 对象类型属性

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}