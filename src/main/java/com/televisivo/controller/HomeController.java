package com.televisivo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping(value = "/home")
    public String home() {
        return "home";
    }

    @GetMapping(value = {"/", "/login"})
    public String loginPage(@AuthenticationPrincipal User user, @RequestParam(value = "error", required = false) String error, Model model) {
        if (user != null) {
            return "redirect:/home";
        }

        if (error != null) {
            model.addAttribute(error, true);
            model.addAttribute("mensagem", "Usuário inválido, ou senha errada!");
        }

        return "login";
    }
}