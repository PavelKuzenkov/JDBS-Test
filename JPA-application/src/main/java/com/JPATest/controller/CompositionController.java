package com.JPATest.controller;

import com.JPATest.data.Composition;
import com.JPATest.service.AlbumService;
import com.JPATest.service.CompositionService;
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
@RequestMapping("/composition")
public class CompositionController {

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Autowired
    private CompositionService compositionService;

    @Autowired
    private AlbumService albumService;


    @GetMapping
    public String showCompositions(Model model, HttpSession session) {
        List<Composition> compositions = new ArrayList<>();
        if (session.getAttribute("find_list") != null) {
            compositions.addAll((Set<Composition>)session.getAttribute("find_list"));
        } else if (session.getAttribute("current_album_id") != null) {
            compositions.addAll(compositionService.getAllByAlbum((Long) session.getAttribute("current_album_id")));
        } else {
            compositions.addAll(compositionService.getAll());
        }
        model.addAttribute("error_message", session.getAttribute("error_message"));
        model.addAttribute("compositions", compositions);
        model.addAttribute("new_composition", new Composition());
        session.setAttribute("error_message", null);
        session.setAttribute("find_list", null);
        return "composition";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("composition") Composition composition, HttpSession session) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<Composition> constraintViolation : validator.validate(composition)) {
            errors.add(constraintViolation.getMessage());
        }
        if (errors.isEmpty()) {
            composition.setAlbum(albumService.getById((Long) session.getAttribute("current_album_id")));
            compositionService.save(composition);
        } else {
            session.setAttribute("error_message", errors);
        }
        return "redirect:/composition";
    }

    @DeleteMapping("/{composition_id}")
    public String delete(@PathVariable("composition_id") Long id) {
        compositionService.delete(id);
        return "redirect:/composition";
    }

    @GetMapping("/find")
    public String find(HttpSession session, @RequestParam(value = "param", required = false, defaultValue = "") String param) {
        if (!param.equals("")) {
            session.setAttribute("find_list", compositionService.getByParam(param));
        }
        return "redirect:/composition";
    }
}
