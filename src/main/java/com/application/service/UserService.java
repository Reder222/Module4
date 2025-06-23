package com.application.service;


import com.application.dataClasses.UserData;
import com.application.dto.UserDTO;
import com.application.dto.UserDTOUtil;
import com.application.repositories.UserRepository;
import com.application.util.ErrorValidationUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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
    public void create(String name, String email, int age) {

        UserData temp = new UserData(name, email, age);

        if (isValid(temp)) {

            userRepository.save(temp);

            kafkaTemplate.send("userService", "create", email);

        }


    }

    @Transactional
    public void create(UserDTO userDTO) {
        create(userDTO.getName(), userDTO.getEmail(), userDTO.getAge());
    }

    public List<UserDTO> getAll() {
        return userRepository.findAll().stream().map(UserDTOUtil::dataToDTO).collect(Collectors.toList());
    }

    public UserDTO getByID(int id) {

        UserData temp = userRepository.findById(id).orElse(null);
        return temp != null ? UserDTOUtil.dataToDTO(temp) : null;
    }

    @Transactional
    public void update(UserDTO user) {
        UserData temp = UserDTOUtil.dtoToData(user);

        if (isValid(temp)) {
            userRepository.save(temp);
        }


    }

    @Transactional
    public void delete(int id) {
        userRepository.findById(id).ifPresent(temp -> {
            userRepository.deleteById(id);
            kafkaTemplate.send("userService", "delete", temp.getEmail());
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
