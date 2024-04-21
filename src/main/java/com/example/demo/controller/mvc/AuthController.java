package com.example.demo.controller.mvc;

import com.example.demo.dto.UserDto;
import com.example.demo.model.Department;
import com.example.demo.model.User;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthController {


    private UserService userService;
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private DepartmentService departmentService;


    // handler method to handle home page request
    @GetMapping("/index")
    public String home() {
        return "index";
    }

    // handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") User user,
                               BindingResult result,
                               Model model){
        User existingUser = userService.findUserByEmail(user.getEmail());
        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }
        if(result.hasErrors()){
            model.addAttribute("user", user);
            return "/register";
        }
//        UserDto dto = userService.mapEntityToDtoUser(user);
//        userService.addUser(dto);
        userService.saveUser(user);
        return "redirect:/register_success";
    }


    // handler method to handle list of users
    @GetMapping("/users")
    public String users(Model model){
        List<UserDto> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "userslist"; }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @ModelAttribute("departmentslist")
    public List<Department> initializeDepartments() {
        return departmentService.getAllDepartments();
    }


}





