package com.myApplication.controller;

import com.myApplication.dao.OrganizationDAO;
import com.myApplication.data.Organization;
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
@RequestMapping("/organization")
public class OrganizationController {

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Autowired
    private OrganizationDAO organizationDAO;


    @GetMapping
    public String showOrganizations(Model model, HttpSession session) {
        model.addAttribute("error_message", session.getAttribute("error_message"));
        model.addAttribute("error_message_db", session.getAttribute("error_message_db"));
        model.addAttribute("organizations", session.getAttribute("find_list") != null ? session.getAttribute("find_list") : organizationDAO.getAllOrganizations());
        model.addAttribute("new_organization", new Organization());
        session.setAttribute("error_message", null);
        session.setAttribute("find_list", null);
        return "organization";
    }

    @PostMapping("/save")
    public String saveInDB(@ModelAttribute("user") Organization organization, HttpSession session) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<Organization> constraintViolation : validator.validate(organization)) {
            errors.add(constraintViolation.getMessage());
        }
        if (errors.isEmpty()) {
            organizationDAO.createOrganization(organization);
        } else {
            session.setAttribute("error_message", errors);
        }
        return "redirect:/organization";
    }

    @GetMapping("/find")
    public String find(HttpSession session, @RequestParam(value = "param", required = false, defaultValue = "") String param) {
        if (!param.equals("")) {
            session.setAttribute("find_list", organizationDAO.findByName(param));
        }
        return "redirect:/organization";
    }
}
