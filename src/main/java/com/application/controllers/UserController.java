package com.application.controllers;


import com.application.dto.UserDTO;
import com.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public void create(@RequestBody UserDTO userDTO) {

        userService.create(userDTO);

    }

    @GetMapping("/")
    public List<UserDTO> findAll(){

        return userService.getAll();

    }

    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable int id){

        return userService.getByID(id);

    }

    @PutMapping("/{id}/edit")
    public void update(@PathVariable int id, @RequestBody UserDTO userDTO) {

        userService.update(userDTO);

    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id){

        userService.delete(id);

    }

}
