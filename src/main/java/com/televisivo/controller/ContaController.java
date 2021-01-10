package com.televisivo.controller;

import java.util.List;

import javax.validation.Valid;

import com.televisivo.model.Categoria;
import com.televisivo.model.Servico;
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
import org.springframework.ui.Model;
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

    private static final String USUARIO = "usuario";
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private static final String ALTERAR = "redirect:/perfil/configuracoes";

    @Autowired
    private ContaService contaService;

    @Autowired
    private UsuarioSerieService usuarioSerieService;

    @GetMapping("/configuracoes")
    public ModelAndView viewAlterar(@AuthenticationPrincipal UsuarioSistema usuarioLogado, SerieFilter serieFilter) {
        ModelAndView modelAndView = new ModelAndView("perfil/perfil");
        modelAndView.addObject(USUARIO, contaService.getOne(usuarioLogado.getUsuario()));
        return modelAndView;
    }

    @GetMapping()
    public ModelAndView detalhes(@AuthenticationPrincipal UsuarioSistema usuarioLogado, SerieFilter serieFilter) {
        ModelAndView modelAndView = new ModelAndView("perfil/detalhes");
        modelAndView.addObject(USUARIO, contaService.getOne(usuarioLogado.getUsuario()));
        return modelAndView;
    }

    @PostMapping("/conta")
    public String alterar(@Valid Usuario usuario, BindingResult result, Model model, @AuthenticationPrincipal UsuarioSistema usuarioLogado, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute(FAIL, "Verifique os campos!");
            return ALTERAR;
        }
        try {
			contaService.update(usuario);
		} catch(EmailCadastradoException e) {
            attributes.addFlashAttribute(FAIL, e.getMessage());
			return ALTERAR;
        }
        attributes.addFlashAttribute(SUCCESS, "Informações alteradas.");
        return ALTERAR;
    }

    @PostMapping("/senha")
    public String alterarSenha(Usuario usuario, BindingResult result, @AuthenticationPrincipal UsuarioSistema usuarioLogado, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute(FAIL, "Verifique os campos!");
            return ALTERAR;
        }
        try {
			contaService.updateSenha(usuario);
		} catch (SenhaError e) {
            attributes.addFlashAttribute(FAIL, e.getMessage());
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
		return usuarioSerieService.findAllCategorias();
    }

    @ModelAttribute("servicos")
	public List<Servico> getServicos() {
		return usuarioSerieService.findAllServicos();
    }
}