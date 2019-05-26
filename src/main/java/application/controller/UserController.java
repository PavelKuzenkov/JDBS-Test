package application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import application.data.User;
import application.service.UserService;


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
    UserService userService;


    @GetMapping
    public String users(Model model, HttpSession session) {
        model.addAttribute("error_message", session.getAttribute("error_message"));
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("new_user", new User());
        session.setAttribute("error_message", null);
        return "user";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") User user, HttpSession session) {
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
}