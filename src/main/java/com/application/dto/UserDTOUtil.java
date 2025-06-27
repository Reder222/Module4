package com.application.dto;


import com.application.dataClasses.UserData;


public class UserDTOUtil {

    public static UserDTO dataToDTO(UserData user) {

        return new UserDTO(user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge());

    }

    public static UserData dtoToData(UserDTO userDTO) {
        UserData temp = new UserData(userDTO.getName(), userDTO.getEmail(), userDTO.getAge());
        temp.setId(userDTO.getId());
        return temp;
    }

}
