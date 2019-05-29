package com.myApplication.controller;

import com.myApplication.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.myApplication.data.User;
import com.myApplication.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserService userService;


    @GetMapping
    public String users(Model model, HttpSession session) {
        model.addAttribute("error_message", session.getAttribute("error_message"));
        model.addAttribute("error_message_db", session.getAttribute("error_message_db"));
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("new_user", new User());
        session.setAttribute("error_message", null);
        session.setAttribute("error_message_db", null);
        return "user";
    }

    @PostMapping("/save_on_disk")
    public String saveOnDisk(@ModelAttribute("user") User user, HttpSession session) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<User> constraintViolation : validator.validate(user)) {
            errors.add(constraintViolation.getMessage());
        }
        if (errors.isEmpty()) {
            userService.toJSON(user);
        } else {
            session.setAttribute("error_message", errors);
        }
        return "redirect:/user";
    }

    @PostMapping("/save_in_db")
    public String saveInDB(@ModelAttribute("user") User user, HttpSession session) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<User> constraintViolation : validator.validate(user)) {
            errors.add(constraintViolation.getMessage());
        }
        if (errors.isEmpty()) {
            userDAO.createUser(user);
        } else {
            session.setAttribute("error_message_db", errors);
        }
        return "redirect:/user";
    }

//    @PostMapping("/create_table")
//    public String createTable() {
//        userDAO.createUsersTable();
//        return "redirect:/user";
//    }
//
//    @PostMapping("/delete_table")
//    public String deleteTable() {
//        userDAO.deleteUsersTable();
//        return "redirect:/user";
//    }
}
