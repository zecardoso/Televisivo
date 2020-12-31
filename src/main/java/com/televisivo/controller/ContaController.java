package com.televisivo.controller;

import com.televisivo.model.enumerate.Genero;
import com.televisivo.security.UsuarioSistema;
import com.televisivo.service.UsuarioService;
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
@RequestMapping("/conta")
public class ContaController {

    private static final String USUARIO = "usuario";
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private static final String DETALHES = "redirect:/conta";
    private static final String ALTERAR = "redirect:/conta/alterar";

    @Autowired
    private UsuarioService contaService;

    @GetMapping("/alterar")
    public ModelAndView viewAlterar(@AuthenticationPrincipal UsuarioSistema usuarioLogado) {
        ModelAndView modelAndView = new ModelAndView("/conta/conta");
        modelAndView.addObject(USUARIO, contaService.getOne(usuarioLogado.getUsuario().getId()));
        return modelAndView;
    }

    @GetMapping()
    public ModelAndView detalhes(@AuthenticationPrincipal UsuarioSistema usuarioLogado) {
        ModelAndView modelAndView = new ModelAndView("/conta/detalhes");
        modelAndView.addObject(USUARIO, contaService.getOne(usuarioLogado.getUsuario().getId()));
        return modelAndView;
    }

    @PostMapping("/alterar")
    public String alterar(@AuthenticationPrincipal UsuarioSistema usuarioLogado, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute(FAIL, "Verifique os campos!");
            return ALTERAR;
        }
        try {
			contaService.update(usuarioLogado.getUsuario());
		} catch(EmailCadastradoException e) {
			result.rejectValue("email", e.getMessage());
			return ALTERAR;
		} catch (SenhaError e) {
            result.rejectValue("password", e.getMessage());
            return ALTERAR;
        }
        attributes.addFlashAttribute(SUCCESS, "Registro alterado com sucesso.");
        return DETALHES;
    }

    @ModelAttribute("generos")
    public Genero[] getGeneros() {
        return Genero.values();
    }

    @PostMapping(value = "/alterar", params = "cancelar")
    public String cancelar() {
        return DETALHES;
    }
}