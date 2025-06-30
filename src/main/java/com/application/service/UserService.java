package com.application.service;


import com.application.dataClasses.UserData;
import com.application.dto.UserDTO;
import com.application.dto.UserDTOUtil;
import com.application.repositories.UserRepository;
import com.application.util.ErrorValidationUtil;
import com.application.util.exceptions.UserNotFoundException;
import com.application.util.exceptions.UserValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserService {

    private KafkaTemplate<String, String> kafkaTemplate;
    private UserRepository userRepository;
    Validator validator;

    @Autowired
    public UserService(UserRepository userRepository, Validator validator, KafkaTemplate<String, String> kafkaTemplate) {
        this.userRepository = userRepository;
        this.validator = validator;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public void create (String name, String email, int age) throws UserValidationException {

        UserData temp = new UserData(name, email, age);

        if (isValid(temp)) {
            userRepository.save(temp);
            if (kafkaTemplate.inTransaction())kafkaTemplate.send("userService", "create", email);
        }
        else throw new UserValidationException(ErrorValidationUtil.getError("UserData"));

    }

    @Transactional
    public void create(UserDTO userDTO) {
        create(userDTO.getName(), userDTO.getEmail(), userDTO.getAge());
    }

    public List<UserDTO> getAll() throws UserNotFoundException {

        List<UserData> userList = userRepository.findAll();

        if(userList.isEmpty()) {
            throw new UserNotFoundException("Users not found");
        }

        return userList.stream().map(UserDTOUtil::dataToDTO).collect(Collectors.toList());
    }

    public UserDTO getByID(int id) throws UserNotFoundException {

        Optional<UserData> temp = userRepository.findById(id);
        if(temp.isPresent()){
            return UserDTOUtil.dataToDTO(temp.get());
        }
        throw new UserNotFoundException("User not found");
    }

    @Transactional
    public void update(UserDTO user) throws UserValidationException{
        UserData temp = UserDTOUtil.dtoToData(user);

        if (isValid(temp)) {
            userRepository.save(temp);
        }

        throw new UserValidationException(ErrorValidationUtil.getError("UserData"));

    }

    @Transactional
    public void delete(int id) {
        userRepository.findById(id).ifPresent(temp -> {
            userRepository.deleteById(id);
            if (kafkaTemplate.inTransaction()) kafkaTemplate.send("userService", "delete", temp.getEmail());
        });

    }

    private boolean isValid(UserData data) {

        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(data);
        if (!constraintViolations.isEmpty()) {
            ErrorValidationUtil.saveError("UserData", constraintViolations);
            return false;
        }

        return true;
    }

}
