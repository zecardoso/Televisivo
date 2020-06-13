package com.televisivo.web.controller;

import java.util.Objects;

import com.televisivo.security.UsuarioSistema;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/home")
    public String home(@AuthenticationPrincipal UsuarioSistema usuarioLogado, ModelMap model) {
        if (usuarioLogado == null) {
            return "/login";
        }
        model.addAttribute("usuarioLogado", usuarioLogado);
        return "home";
    }

    @RequestMapping(value = { "/", "/login" }, method = { RequestMethod.GET, RequestMethod.POST })
    public String loginPage(@RequestParam(value = "mensagem", required = false) String mensagem, Model model) {
        if (Objects.isNull(mensagem)) {
            model.addAttribute("acao", false);
        } else if (mensagem.trim().equals("true")) {
            model.addAttribute("acao", false);
            model.addAttribute("mensagem", "Sessão inválida!");
        } else {
            model.addAttribute("acao", true);
            model.addAttribute("mensagem", mensagem);
        }
        return "login";
    }

    @GetMapping("/403")
    public String accessDaniedHandler() {
        return "403";
    }
}