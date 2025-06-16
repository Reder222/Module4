package com.application.dto;


import com.application.dataClasses.UserData;

import java.time.LocalDate;

public class UserDTOUtil {

    public static UserDTO dataToDTO(UserData user) {

        return new UserDTO(user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge(),
                user.getRegistrationDate()
        );

    }

    public static UserData dtoToData(UserDTO userDTO) {
        UserData temp = new UserData(userDTO.getName(), userDTO.getEmail(), userDTO.getAge());
        temp.setRegistrationDate(userDTO.getCreated_at());
        temp.setId(userDTO.getId());
        return temp;
    }

    public static class UserDTO {

        private int id;
        private String name;
        private String email;
        private int age;
        private LocalDate created_at;

        public UserDTO(int id, String name, String email, int age, LocalDate created_at){
            this.id = id;
            this.name = name;
            this.email = email;
            this.age = age;
            this.created_at = created_at;
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

        public LocalDate getCreated_at() {
            return created_at;
        }

        public void setCreated_at(LocalDate created_at) {
            this.created_at = created_at;
        }

        @Override
        public String toString() {
            return "User: id=" + id + ", name=" + name + ", email=" + email + ", age=" + age;
        }

    }

}
