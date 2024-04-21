package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.model.Department;
import com.example.demo.model.Document;
import com.example.demo.model.User;


import java.util.Collection;
import java.util.List;

public interface UserService {

    UserDto addUser(UserDto userDto);

    UserDto getUserById(Long id);

    List<UserDto> getUserByLastName(String lName);
    List<UserDto> getAllUsers();

    UserDto updateUser(Long id, UserDto userDto);

    User update(Long id, User user);

    String deleteUser(Long userId);


    List<Document> getDocumentsByUserId(long id);

    UserDto mapEntityToDtoUser(User user);

    User findUserByEmail(String email);

    void saveUser(User user);

    List<UserDto> search(String keyword);

    Collection<? extends UserDto> getUsersByDepartment(Department department);
}
