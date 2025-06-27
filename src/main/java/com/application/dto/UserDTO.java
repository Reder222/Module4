package com.application.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

public class UserDTO {

    private int id;
    private String name;
    private String email;
    private int age;


    public UserDTO(int id, String name, String email, int age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;

    }

    public UserDTO(String name, String email, int age) {
        id =0;
        this.name = name;
        this.email = email;
        this.age = age;
    }
    public UserDTO() {}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return "User: id=" + id + ", name=" + name + ", email=" + email + ", age=" + age;
    }

}
