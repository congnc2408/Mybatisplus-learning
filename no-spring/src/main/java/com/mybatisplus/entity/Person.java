package com.mybatisplus.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Person {
    private Long id;
    private String name;
    private int age;
}
