package com.televisivo.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.televisivo.config.TelevisivoConfig;
import com.televisivo.controller.page.PaginaWrapper;
import com.televisivo.model.Role;
import com.televisivo.model.Usuario;
import com.televisivo.model.enumerate.Genero;
import com.televisivo.repository.filters.UsuarioFilter;
import com.televisivo.service.RoleService;
import com.televisivo.service.UsuarioService;
import com.televisivo.service.exceptions.EmailExistente;
import com.televisivo.service.exceptions.SenhaError;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/lista", method = RequestMethod.GET)
    public ModelAndView lista(UsuarioFilter usuarioFilter, HttpServletRequest httpServletRequest, @RequestParam(value = "page", required = false) Optional<Integer> page, @RequestParam(value = "size", required = false) Optional<Integer> size) {
        Pageable pageable = PageRequest.of(page.orElse(TelevisivoConfig.INITIAL_PAGE), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        PaginaWrapper<Usuario> paginaWrapper = new PaginaWrapper(usuarioService.listaComPaginacao(usuarioFilter, pageable), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE), httpServletRequest);
        ModelAndView modelAndView = new ModelAndView("/usuario/lista");
        modelAndView.addObject("pageSizes", TelevisivoConfig.PAGE_SIZES);
        modelAndView.addObject("size", size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        modelAndView.addObject("pagina", paginaWrapper);
        return modelAndView;
    }

    @RequestMapping(value = "/adicionar", method = RequestMethod.GET)
    public ModelAndView cadastro(Usuario usuario) {
        ModelAndView modelAndView = new ModelAndView("/usuario/usuario");
        modelAndView.addObject("usuario", usuario);
        return modelAndView;
    }

    @RequestMapping(value = "/adicionar", method = RequestMethod.POST)
    public ModelAndView adicionar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return cadastro(usuario);
        }
        try {
            usuario = usuarioService.adicionar(usuario);
        } catch (EmailExistente e) {
            result.rejectValue("email", e.getMessage());
            return cadastro(usuario);
        } catch (SenhaError e) {
            result.rejectValue("senha", e.getMessage());
            return cadastro(usuario);
        }
        attributes.addFlashAttribute("success", "Registro adicionado com sucesso.");
        return new ModelAndView("redirect:/usuario/lista");
    }

    @RequestMapping(value = "/alterar", method = RequestMethod.POST)
    public ModelAndView alterar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return cadastro(usuario);
        }
        usuarioService.alterar(usuario);
        attributes.addFlashAttribute("success", "Registro alterado com sucesso.");
        return new ModelAndView("redirect:/usuario/lista");
    }
    
    @RequestMapping(value = "/remover", method = RequestMethod.POST)
    public ModelAndView remover(Usuario usuario, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return cadastro(usuario);
        }
        usuarioService.remover(usuario);
        attributes.addFlashAttribute("success", "Registro removido com sucesso.");
        return new ModelAndView("redirect:/usuario/lista");
    }

    @RequestMapping(value = "/detalhes/{id}", method = RequestMethod.GET)
    public ModelAndView detalhes(@PathVariable("id") Long id) {
        Usuario usuario = usuarioService.buscarId(id);
        ModelAndView modelAndView = new ModelAndView("/usuario/detalhes");
        modelAndView.addObject("usuario", usuario);
        return modelAndView;
    }

    @RequestMapping(value = "/alterar/{id}", method = RequestMethod.GET)
    public ModelAndView buscar(@PathVariable("id") Long id) {
        Usuario usuario = usuarioService.buscarId(id);
        return cadastro(usuario);
    }

    @RequestMapping(value = "/remover/{id}", method = RequestMethod.GET)
    public ModelAndView removerId(@PathVariable("id") Long id) {
        Usuario usuario = usuarioService.buscarId(id);
        ModelAndView modelAndView = new ModelAndView("/usuario/remover");
        modelAndView.addObject("usuario", usuario);
        return modelAndView;
    }

    @ModelAttribute("roles")
    public List<Role> getRoles() {
        return roleService.listar();
    }

    @ModelAttribute("generos")
    public Genero[] getGeneros() {
        return Genero.values();
    }

    @RequestMapping(value = {"/adicionar", "/alterar", "/remover"}, method = RequestMethod.POST, params = "action=cancelar")
	public String cancelar() {
		return "redirect:/usuario/lista";
	}
}