package com.example.demo.controller;


import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {


    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public UserDto findUserById(@PathVariable(value = "id") long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public UserDto saveUser(@RequestBody UserDto userDto) {
        UserDto result = null;
        try {
            result = userService.addUser(userDto);
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable(value = "id") long id, @RequestBody UserDto userDto) {
        UserDto result = null;
        try {
            result = userService.updateUser(id, userDto);
        } catch (Exception e) {
            return null;
        }
        return result;
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(name = "id") Long studentId) {
        String message = userService.deleteUser(studentId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


}
