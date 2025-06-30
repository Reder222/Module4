package com.application.controllers;


import com.application.dto.UserDTO;
import com.application.service.UserService;
import com.application.util.ErrorMessageEntity;
import com.application.util.exceptions.UserNotFoundException;
import com.application.util.exceptions.UserValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> create(@RequestBody UserDTO userDTO) {

        userService.create(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @GetMapping("/")
    public List<UserDTO> findAll() {

        return userService.getAll();

    }

    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable int id) {

        return userService.getByID(id);

    }

    @PostMapping("/{id}/edit")
    public ResponseEntity<HttpStatus> update(@PathVariable int id,@RequestBody UserDTO userDTO) {

        userService.update(userDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable int id) {

        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @ExceptionHandler
    private ResponseEntity<ErrorMessageEntity> userNotFound(UserNotFoundException e) {
        ErrorMessageEntity message = new ErrorMessageEntity(e.getMessage());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorMessageEntity> userInvalidValidation(UserValidationException e) {
        ErrorMessageEntity message = new ErrorMessageEntity(e.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorMessageEntity> unknownException(Exception e) {
        ErrorMessageEntity message = new ErrorMessageEntity(e.getMessage());
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
