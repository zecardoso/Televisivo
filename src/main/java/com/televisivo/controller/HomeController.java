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
    public String loginPage(@AuthenticationPrincipal User user, @RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout, Model model) {
        if (user != null) {
            return "redirect:/home";
        } else if (error != null) {
            model.addAttribute("mensagem", "Usuário inválido, ou senha errada!");
        } else if (logout != null) {
            model.addAttribute("mensagem", "Logout realizao com sucesso!");
        } else {
            model.addAttribute("mensagem", "Sessão expirada, faça o login novamente!");
        }
        model.addAttribute("acao", true);
        return "login";
    }
}