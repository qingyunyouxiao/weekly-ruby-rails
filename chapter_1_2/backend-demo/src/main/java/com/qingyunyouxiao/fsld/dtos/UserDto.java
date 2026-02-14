package com.qingyunyouxiao.fsld.dtos;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users") // 指定 MongoDB 中的集合名称
public class UserDto {
    @Id // 指定主键字段
    private String id;
    private String name; // 用户名
    private String password; // 密码

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
