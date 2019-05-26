package application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import application.data.User;
import application.service.UserService;

@Controller
@RequestMapping("/user")
public class UserContrioller {

    @Autowired
    UserService userService;


    @GetMapping
    public String users(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("new_user", new User());
        return "user";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") User user) {
        userService.toJSON(user);
        return "redirect:/user";
    }
}
