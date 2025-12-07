package com.mybatisplus.sampleactiverecord.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper=false)
@Data
@Accessors(chain = true)
@TableName("sys_user")
public class User extends Model<User> { 
    private Long id;
    private String name;
    private Integer age;
    private String email;



}
