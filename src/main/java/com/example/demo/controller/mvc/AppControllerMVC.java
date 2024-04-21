package com.example.demo.controller.mvc;


import com.example.demo.dto.UserDto;
import com.example.demo.model.UserProfile;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;


@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class AppControllerMVC {

    @Autowired
    UserService userService;


    @ModelAttribute("usersList")
    public List<UserDto> users() {return userService.getAllUsers();
    }

    @RequestMapping(value = "/allusers", method = RequestMethod.GET)
    public String all() {
       return "users";
    }
}
