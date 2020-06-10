package com.televisivo.web.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.televisivo.config.TelevisivoConfig;
import com.televisivo.model.Role;
import com.televisivo.model.Usuario;
import com.televisivo.model.enumerate.Genero;
import com.televisivo.repository.filters.UsuarioFilter;
import com.televisivo.repository.pagination.Pagina;
import com.televisivo.service.RoleService;
import com.televisivo.service.UsuarioService;
import com.televisivo.service.exceptions.EmailCadastradoException;
import com.televisivo.service.exceptions.SenhaError;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/usuario")
public class UsuarioController {

    private static final String USUARIO = "usuario";
    private static final String SUCCESS = "success";
    private static final String LISTA = "redirect:/usuario/lista";
    
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/lista")
    public ModelAndView lista(UsuarioFilter usuarioFilter, HttpServletRequest httpServletRequest, @RequestParam(value = "page", required = false) Optional<Integer> page, @RequestParam(value = "size", required = false) Optional<Integer> size) {
        Pageable pageable = PageRequest.of(page.orElse(TelevisivoConfig.INITIAL_PAGE), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        Pagina<Usuario> pagina = new Pagina<>(usuarioService.listaComPaginacao(usuarioFilter, pageable), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE), httpServletRequest);
        ModelAndView modelAndView = new ModelAndView("/usuario/lista");
        modelAndView.addObject("pageSizes", TelevisivoConfig.PAGE_SIZES);
        modelAndView.addObject("size", size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        modelAndView.addObject("pagina", pagina);
        return modelAndView;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastro(Usuario usuario) {
        ModelAndView modelAndView = new ModelAndView("/usuario/usuario");
        modelAndView.addObject(USUARIO, usuario);
        return modelAndView;
    }

    @GetMapping("/detalhes/{id}")
    public ModelAndView detalhes(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/usuario/detalhes");
        Usuario usuario = usuarioService.getOne(id);
        modelAndView.addObject(USUARIO, usuario);
        return modelAndView;
    }

    @GetMapping("/alterar/{id}")
    public ModelAndView alterarId(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/usuario/usuario");
        Usuario usuario = usuarioService.getOne(id);
        modelAndView.addObject(USUARIO, usuario);
        return modelAndView;
    }

    @GetMapping("/remover/{id}")
    public ModelAndView removerId(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/usuario/remover");
        Usuario usuario = usuarioService.getOne(id);
        modelAndView.addObject(USUARIO, usuario);
        return modelAndView;
    }

    @PostMapping("/adicionar")
    public ModelAndView adicionar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return cadastro(usuario);
        }
        try {
            usuarioService.save(usuario);
        } catch (EmailCadastradoException e) {
            result.rejectValue("email", e.getMessage(), e.getMessage());
            return cadastro(usuario);
        } catch (SenhaError e) {
            result.rejectValue("senha", e.getMessage(), e.getMessage());
            return cadastro(usuario);
        }
        attributes.addFlashAttribute(SUCCESS, "Registro adicionado com sucesso.");
        return new ModelAndView(LISTA);
    }

    @PostMapping("/alterar")
    public ModelAndView alterar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return cadastro(usuario);
        }
        try {
			usuarioService.update(usuario);	
		} catch(EmailCadastradoException e) {
			result.rejectValue("email", e.getMessage(), e.getMessage());
			return cadastro(usuario);
		}
        attributes.addFlashAttribute(SUCCESS, "Registro alterado com sucesso.");
        return new ModelAndView(LISTA);
    }
    
    @PostMapping("/remover")
    public ModelAndView remover(Usuario usuario, BindingResult result, RedirectAttributes attributes) {
        usuarioService.deleteById(usuario.getId());
        attributes.addFlashAttribute(SUCCESS, "Registro removido com sucesso.");
        return new ModelAndView(LISTA);
    }

    @ModelAttribute("roles")
    public List<Role> getRoles() {
        return roleService.findAll();
    }

    @ModelAttribute("generos")
    public Genero[] getGeneros() {
        return Genero.values();
    }

    @PostMapping(value = {"/adicionar", "/alterar", "/remover"}, params = "action=cancelar")
	public String cancelar() {
		return LISTA;
	}
}