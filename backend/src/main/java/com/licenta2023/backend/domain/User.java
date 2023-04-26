package com.licenta2023.backend.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class User extends Entity implements Serializable {
    private String email;
    private Integer age;
    private String name;
    private String password;

    public String getEmail() { return email; }

    public Integer getAge() { return age; }
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
    private Set<Review> reviews = new HashSet<>();

    public User(String email, String password, Integer age, String name) {
        this.email = email;
        this.password = password;
        this.age = age;
        this.name = name;
    }
    public User() {
        super();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
