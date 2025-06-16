package com.application.dataClasses;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;



import java.time.LocalDate;

@Entity
@Table(name = "UserData")
public class UserData {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "Name", nullable = false)
    @NotNull(message = "Name cant be null")
    @Size (min = 2,max = 30, message ="Name should be between 2 and 30 characters" )
    private String name;


    @Column(name = "email", nullable = false, unique = true)
    @NotNull(message = "Email cant be null")
    private String email;


    @Column(name = "Age", nullable = false)
    @Min(value = 0, message = "Age should be greater than 0")
    @Max(value = 100, message = "Age should be lower than 100")
    private int age;

    @Column(name = "Registration_date", nullable = false)
    @NotNull(message = "Data of creation cant be null")
    private LocalDate created_at;

    public UserData() {
    }

    public UserData(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
        created_at = LocalDate.now();
    }

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

    public LocalDate getRegistrationDate() {
        return created_at;
    }
    public void setRegistrationDate(LocalDate created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "User: id=" + id + ", name=" + name + ", email=" + email + ", age=" + age;
    }

    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof UserData)) return false;
        return id == ((UserData) obj).id;
    }
}
