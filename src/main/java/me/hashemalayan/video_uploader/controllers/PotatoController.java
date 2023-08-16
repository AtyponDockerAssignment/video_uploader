package me.hashemalayan.video_uploader.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class PotatoController {
    @PostMapping(value = "/login")
    public String login() {
        return "redirect:uploadForm";
    }

    @PostMapping(value = "/signup")
    public String signup() {

        return "redirect:uploadForm";
    }
}
