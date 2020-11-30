package com.televisivo.web.controller;

import javax.servlet.http.HttpServletRequest;

import com.televisivo.security.UsuarioSistema;
import com.televisivo.service.UsuarioEpisodioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/series/{id}/temporada")
public class UsuarioEpisodioController {

    private static final String SUCCESS = "success";

    @Autowired
    private UsuarioEpisodioService usuarioEpisodioService;

    @PostMapping(value = "{id}/detalhes", params = "marcarEpisodio")
    public String marcarEpisodio(@PathVariable("id") Long id, @AuthenticationPrincipal UsuarioSistema usuarioLogado, HttpServletRequest request, RedirectAttributes attributes) {
        usuarioEpisodioService.marcarEpisodio(usuarioLogado, Long.parseLong(request.getParameter("marcarEpisodio")));
        attributes.addFlashAttribute(SUCCESS, "Episódio marcado com sucesso.");
        return "redirect:./detalhes";
    }

    @PostMapping(value = "{id}/detalhes", params = "desmarcarEpisodio")
    public String desmarcarEpisodio(@PathVariable("id") Long id, @AuthenticationPrincipal UsuarioSistema usuarioLogado, HttpServletRequest request, RedirectAttributes attributes) {
        usuarioEpisodioService.desmarcarEpisodio(usuarioLogado, Long.parseLong(request.getParameter("desmarcarEpisodio")));
        attributes.addFlashAttribute(SUCCESS, "Episódio desmarcado com sucesso.");
        return "redirect:./detalhes";
    }
}