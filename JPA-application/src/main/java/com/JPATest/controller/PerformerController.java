package com.JPATest.controller;

import com.JPATest.data.Performer;
import com.JPATest.service.GroupService;
import com.JPATest.service.PerformerService;
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
import java.util.Set;

@Controller
@RequestMapping("/performer")
public class PerformerController {

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Autowired
    private PerformerService performerService;

    @Autowired
    private GroupService groupService;


    @GetMapping
    public String showPerformers(Model model, HttpSession session) {
        List<Performer> performers = new ArrayList<>();
        if (session.getAttribute("find_list") != null) {
            performers.addAll((Set<Performer>)session.getAttribute("find_list"));
        } else if (session.getAttribute("current_group_id") != null) {
            performers.addAll(performerService.getAllByGroup((Long) session.getAttribute("current_group_id")));
        } else {
            performers.addAll(performerService.getAll());
        }
        model.addAttribute("error_message", session.getAttribute("error_message"));
        model.addAttribute("performers", performers);
        model.addAttribute("new_performer", new Performer());
        session.setAttribute("error_message", null);
        session.setAttribute("find_list", null);
        return "performer";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("performer") Performer performer, HttpSession session) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<Performer> constraintViolation : validator.validate(performer)) {
            errors.add(constraintViolation.getMessage());
        }
        if (errors.isEmpty()) {
            performer.setGroup(groupService.getById((Long) session.getAttribute("current_group_id")));
            performerService.save(performer);
        } else {
            session.setAttribute("error_message", errors);
        }
        return "redirect:/performer";
    }

    @DeleteMapping("/{performer_id}")
    public String delete(@PathVariable("performer_id") Long id) {
        performerService.delete(id);
        return "redirect:/performer";
    }

    @GetMapping("/find")
    public String find(HttpSession session, @RequestParam(value = "param", required = false, defaultValue = "") String param) {
        if (!param.equals("")) {
            session.setAttribute("find_list", performerService.getByParam(param));
        }
        return "redirect:/performer";
    }
}
