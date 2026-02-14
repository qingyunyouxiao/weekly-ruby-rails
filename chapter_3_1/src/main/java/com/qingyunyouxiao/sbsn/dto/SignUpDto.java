package com.qingyunyouxiao.sbsn.dto;

import jakarta.validation.constraints.NotEmpty;

public class SignUpDto {
    
    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String login;

    @NotEmpty
    private char[] password;

    public SignUpDto() {
        super();
    }

    public SignUpDto(@NotEmpty String firstName, @NotEmpty String lastName, @NotEmpty String login, @NotEmpty char[]
    password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

        public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }
}

/**
 * 关于 @NotEmpty、@NotBlank、@NotNull 注解的使用方法与区别：
 * https://developer.aliyun.com/article/1349701
 * 
 */