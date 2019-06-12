package com.JPATest.controller;

import com.JPATest.data.Group;
import com.JPATest.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/group")
public class GroupController {

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Autowired
    private GroupService groupService;


    @GetMapping
    public String showGroups(Model model, HttpSession session) {
        model.addAttribute("error_message", session.getAttribute("error_message"));
        model.addAttribute("groups", session.getAttribute("find_list") != null ? session.getAttribute("find_list") : groupService.getAll());
        model.addAttribute("new_group", new Group());
        session.setAttribute("error_message", null);
        session.setAttribute("find_list", null);
        return "group";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("group") Group group, HttpSession session) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<Group> constraintViolation : validator.validate(group)) {
            errors.add(constraintViolation.getMessage());
        }
        if (errors.isEmpty()) {
            groupService.save(group);
        } else {
            session.setAttribute("error_message", errors);
        }
        return "redirect:/group";
    }

    @DeleteMapping("/{group_id}")
    public String delete(@PathVariable("group_id") Long id) {
        groupService.delete(id);
        return "redirect:/group";
    }

    @GetMapping("/group_albums/{group_id}")
    public String getAlbums(@PathVariable("group_id") Long id, HttpSession session) {
        session.setAttribute("current_group_id", id);
        return "redirect:/album";
    }

    @GetMapping("/group_performers/{group_id}")
    public String getPerformers(@PathVariable("group_id") Long id, HttpSession session) {
        session.setAttribute("current_group_id", id);
        return "redirect:/performer";
    }

    @GetMapping("/find")
    public String find(HttpSession session, @RequestParam(value = "param", required = false, defaultValue = "") String param) {
        if (!param.equals("")) {
            session.setAttribute("find_list", groupService.getByParam(param));
        }
        return "redirect:/group";
    }
}
