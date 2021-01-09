package com.televisivo.controller;

import java.util.List;

import javax.validation.Valid;

import com.televisivo.model.Categoria;
import com.televisivo.model.Usuario;
import com.televisivo.model.enumerate.Genero;
import com.televisivo.repository.filters.SerieFilter;
import com.televisivo.security.UsuarioSistema;
import com.televisivo.service.ContaService;
import com.televisivo.service.UsuarioSerieService;
import com.televisivo.service.exceptions.EmailCadastradoException;
import com.televisivo.service.exceptions.SenhaError;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/perfil")
public class ContaController {

    private static final String USUARIO = "usuarioLogado";
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private static final String DETALHES = "redirect:/perfil";
    private static final String ALTERAR = "redirect:/perfil/configuracoes";

    @Autowired
    private ContaService contaService;

    @Autowired
    private UsuarioSerieService usuarioSerieService;

    @GetMapping("/configuracoes")
    public ModelAndView viewAlterar(@AuthenticationPrincipal UsuarioSistema usuarioLogado, SerieFilter serieFilter) {
        ModelAndView modelAndView = new ModelAndView("perfil/perfil");
        modelAndView.addObject(USUARIO, contaService.getOne(usuarioLogado.getUsuario().getId()));
        return modelAndView;
    }

    @GetMapping()
    public ModelAndView detalhes(@AuthenticationPrincipal UsuarioSistema usuarioLogado, SerieFilter serieFilter) {
        ModelAndView modelAndView = new ModelAndView("perfil/detalhes");
        modelAndView.addObject(USUARIO, contaService.getOne(usuarioLogado.getUsuario().getId()));
        return modelAndView;
    }

    @PostMapping("/conta")
    public String alterar(@Valid Usuario usuario, @AuthenticationPrincipal UsuarioSistema usuarioLogado, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute(FAIL, "Verifique os campos!");
            return ALTERAR;
        }
        try {
			contaService.update(usuario);
		} catch(EmailCadastradoException e) {
			result.rejectValue("email", e.getMessage());
			return ALTERAR;
        }
        attributes.addFlashAttribute(SUCCESS, "Informações alteradas.");
        return ALTERAR;
    }

    @PostMapping("/senha")
    public String alterarSenha(@Valid Usuario usuario, @AuthenticationPrincipal UsuarioSistema usuarioLogado, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute(FAIL, "Verifique os campos!");
            return ALTERAR;
        }
        try {
			contaService.updateSenha(usuarioLogado.getUsuario());
		} catch (SenhaError e) {
            result.rejectValue("password", e.getMessage());
            return ALTERAR;
        }
        attributes.addFlashAttribute(SUCCESS, "Senha alterada.");
        return ALTERAR;
    }

    @ModelAttribute("generos")
    public Genero[] getGeneros() {
        return Genero.values();
    }

    @ModelAttribute("categorias")
	public List<Categoria> getCategorias() {
		return usuarioSerieService.findAll();
    }

    @PostMapping(value = "/alterar", params = "cancelar")
    public String cancelar() {
        return DETALHES;
    }
}