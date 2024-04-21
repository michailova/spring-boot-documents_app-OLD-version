package com.example.demo.controller.mvc;

import com.example.demo.dto.UserDto;
import com.example.demo.model.*;
import com.example.demo.service.*;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;

@Controller
@RequestMapping("/users")
public class UserControllerMVC {

    Logger logger = LoggerFactory.getLogger(UserControllerMVC.class);
    @Autowired
    UserService userService;
    @Autowired
    UserProfileService userProfileService;

    @Autowired
    DepartmentService departmentService;


    @Autowired
    MailSenderService mailSenderService;

    @Autowired
    NoteService noteService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @GetMapping(value = {"/", "/list"})
    public String listUsers(ModelMap model) {

        List<UserDto> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "userslist";
    }

    @GetMapping("/adduser")
    public String showForm(Model model) {
        logger.info("showForm started");
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }

    @RequestMapping(value = {"/newuser"}, method = RequestMethod.POST)
    public String saveUser(User user, BindingResult result,
                           ModelMap model) {
        if (result.hasErrors()) {
            return "error";

        }
        UserDto dto = userService.mapEntityToDtoUser(user);
        userService.addUser(dto);
        model.addAttribute("success", "User " + dto.getFirstName() + " " + dto.getLastName() + " registered successfully");
        mailSenderService.sendNewMail(dto.getEmail(), "welcomeAuthentication in documents app", "for authentication in document application" +
                " follow the link http://localhost:8088/login you password for first enter: " + dto.getPerson_number() + dto.getFirstName());
        return "register_success";


    }
    @ModelAttribute("roles")
    public List<UserProfile> initializeProfiles() {
        return userProfileService.findAllProfiles();
    }

    @ModelAttribute("departmentslist")
    public List<Department> initializeDepartments() {
        return departmentService.getAllDepartments();
    }


    @RequestMapping(value = {"/delete-user-{userId}"}, method = RequestMethod.GET)
    public String deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return "redirect:/users/list";
    }


    @RequestMapping(value = {"/update-user-{userId}"}, method = RequestMethod.GET)
    public String updateUser(Model model, @PathVariable Long userId) {
        logger.info("updateUser started");
        UserDto userDto = userService.getUserById(userId);
        model.addAttribute("user", userDto);
        return "updatedeuser";
    }

    @RequestMapping(value = {"/update-user-{userId}"}, method = RequestMethod.POST)
    public String updateUser(UserDto userDto, BindingResult result,
                                   ModelMap model, @PathVariable Long userId) {

        if (result.hasErrors()) {
            return "registration";
        }

        userService.updateUser(userId, userDto);

        model.addAttribute("success", "User " + userDto.getFirstName() + " " + userDto.getLastName() +  " updated successfully");
        return "redirect:/users/list";
    }

    @RequestMapping(value = {"/view-documents-{userId}"}, method = RequestMethod.GET)
    public String showUserDocument(Model model, @PathVariable Long userId) {
        logger.info("showUserDocument started");
        List<Document> documents = userService.getDocumentsByUserId(userId);
        model.addAttribute("documents", documents);
        return "documentslist";
    }

    @RequestMapping(value = {"/updatepassword"}, method = RequestMethod.GET) // TODO: 07.04.2024
    public String updatePassword(Model model) {
        logger.info("updatePassword started");
        final String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByEmail(currentUserEmail);
        model.addAttribute("user", user);
        return "updatepasswordform";
    }

    @RequestMapping(value = {"/updatepassword"}, method = RequestMethod.POST)
    public String updatePassword(ModelMap model) {
        final String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByEmail(currentUserEmail);

        userService.update(user.getId(), user);
       model.addAttribute("success", "User " + user.getFirst_name() + " " + user.getLast_name() +
               " password updated successfully");
        return "redirect:/documents/list";
    }

    @RequestMapping("/search")
    public String search(@RequestParam String keyword, Model model) {
        List<UserDto> result = userService.search(keyword);
        model.addAttribute("result", result);
        return "search";
    }

    @RequestMapping(value = {"/view-user-{uId}"}, method = RequestMethod.GET)
    public String showUserDocument(Model model, @PathVariable Integer uId) {
        logger.info("showUserDocument started");
        List<Note> notes = noteService.getNotesByUserId(uId);
        model.addAttribute("notes", notes);
        return "notelist";
    }

    @RequestMapping("/information")
    public String information(Model model) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByEmail(currentUserEmail);
        model.addAttribute("user", user);
        return "information";
    }

    @RequestMapping(value = {"/view-documents"}, method = RequestMethod.GET)
    public String viewDocument(Model model) {
        logger.info("viewDocument started");
        final String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByEmail(currentUserEmail);
        List<Document> documents = userService.getDocumentsByUserId(user.getId());
        model.addAttribute("documents", documents);
        return "documentslist";
    }



}

