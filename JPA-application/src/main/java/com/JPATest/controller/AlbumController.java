package com.JPATest.controller;

import com.JPATest.data.Album;
import com.JPATest.service.AlbumService;
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
import java.util.Set;

@Controller
@RequestMapping("/album")
public class AlbumController {

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Autowired
    private AlbumService albumService;

    @Autowired
    private GroupService groupService;


    @GetMapping
    public String showAlbums(Model model, HttpSession session) {
        List<Album> albums = new ArrayList<>();
        if (session.getAttribute("find_list") != null) {
            albums.addAll((Set<Album>)session.getAttribute("find_list"));
        } else if (session.getAttribute("current_group_id") != null) {
            albums.addAll(albumService.getAllByGroup((Long) session.getAttribute("current_group_id")));
        } else {
            albums.addAll(albumService.getAll());
        }
        model.addAttribute("error_message", session.getAttribute("error_message"));
        model.addAttribute("albums", albums);
        model.addAttribute("new_album", new Album());
        session.setAttribute("error_message", null);
        session.setAttribute("find_list", null);
        return "album";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("album") Album album, HttpSession session) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<Album> constraintViolation : validator.validate(album)) {
            errors.add(constraintViolation.getMessage());
        }
        if (errors.isEmpty()) {
            album.setGroup(groupService.getById((Long) session.getAttribute("current_group_id")));
            albumService.save(album);
        } else {
            session.setAttribute("error_message", errors);
        }
        return "redirect:/album";
    }

    @DeleteMapping("/{album_id}")
    public String delete(@PathVariable("album_id") Long id) {
        albumService.delete(id);
        return "redirect:/album";
    }

    @GetMapping("/album_compositions/{album_id}")
    public String getCompositions(@PathVariable("album_id") Long id, HttpSession session) {
        session.setAttribute("current_album_id", id);
        return "redirect:/composition";

    }

    @GetMapping("/find")
    public String find(HttpSession session, @RequestParam(value = "param", required = false, defaultValue = "") String param) {
        if (!param.equals("")) {
            session.setAttribute("find_list", albumService.getByParam(param));
        }
        return "redirect:/album";
    }
}
