package com.application.dto;


import com.application.dataClasses.UserData;


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

}
