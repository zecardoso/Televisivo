package com.televisivo.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.televisivo.config.TelevisivoConfig;
import com.televisivo.controller.page.PaginaWrapper;
import com.televisivo.model.Role;
import com.televisivo.repository.filters.RoleFilter;
import com.televisivo.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/lista", method = RequestMethod.GET)
    public ModelAndView lista(RoleFilter roleFilter, HttpServletRequest httpServletRequest, @RequestParam(value = "page", required = false) Optional<Integer> page, @RequestParam(value = "size", required = false) Optional<Integer> size) {
        Pageable pageable = PageRequest.of(page.orElse(TelevisivoConfig.INITIAL_PAGE), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        PaginaWrapper<Role> paginaWrapper = new PaginaWrapper(roleService.listaComPaginacao(roleFilter, pageable), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE), httpServletRequest);
        ModelAndView modelAndView = new ModelAndView("/role/lista");
        modelAndView.addObject("pageSizes", TelevisivoConfig.PAGE_SIZES);
        modelAndView.addObject("size", size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        modelAndView.addObject("pagina", paginaWrapper);
        return modelAndView;
    }

    @RequestMapping(value = "/adicionar", method = RequestMethod.GET)
    public ModelAndView cadastro(Role role) {
        ModelAndView modelAndView = new ModelAndView("/role/role");
        modelAndView.addObject("role", role);
        return modelAndView;
    }

    @RequestMapping(value = "/adicionar", method = RequestMethod.POST)
    public ModelAndView adicionar(@Valid Role role, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return cadastro(role);
        }
        role = roleService.adicionar(role);
        attributes.addFlashAttribute("success", "Registro adicionado com sucesso.");
        return new ModelAndView("redirect:/role/lista");
    }

    @RequestMapping(value = "/alterar", method = RequestMethod.POST)
    public ModelAndView alterar(@Valid Role role, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return cadastro(role);
        }
        roleService.alterar(role);
        attributes.addFlashAttribute("success", "Registro alterado com sucesso.");
        return new ModelAndView("redirect:/role/lista");
    }

    @RequestMapping(value = "/remover", method = RequestMethod.POST)
    public ModelAndView remover(Role role, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return cadastro(role);
        }
        roleService.remover(role);
        attributes.addFlashAttribute("success", "Registro removido com sucesso.");
        return new ModelAndView("redirect:/role/lista");
    }

    @RequestMapping(value = "/detalhes/{id}", method = RequestMethod.GET)
    public ModelAndView detalhes(@PathVariable("id") Long id) {
        Role role = roleService.buscarId(id);
        ModelAndView modelAndView = new ModelAndView("/role/detalhes");
        modelAndView.addObject("role", role);
        return modelAndView;
    }

    @RequestMapping(value = "/alterar/{id}", method = RequestMethod.GET)
    public ModelAndView buscar(@PathVariable("id") Long id) {
        Role role = roleService.buscarId(id);
        return cadastro(role);
    }

    @RequestMapping(value = "/remover/{id}", method = RequestMethod.GET)
    public ModelAndView removerId(@PathVariable("id") Long id) {
        Role role = roleService.buscarId(id);
        ModelAndView modelAndView = new ModelAndView("/role/remover");
        modelAndView.addObject("role", role);
        return modelAndView;
    }

    @RequestMapping(value = {"/adicionar", "/alterar", "/remover"}, method = RequestMethod.POST, params = "action=cancelar")
	public String cancelar() {
		return "redirect:/role/lista";
	}
}