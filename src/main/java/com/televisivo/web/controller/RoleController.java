package com.televisivo.web.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.televisivo.config.TelevisivoConfig;
import com.televisivo.model.Role;
import com.televisivo.repository.filters.RoleFilter;
import com.televisivo.repository.pagination.Pagina;
import com.televisivo.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/role")
public class RoleController {

    private static final String ROLE = "role";
    private static final String SUCCESS = "success";
    private static final String DETALHES = "redirect:./detalhes";
    private static final String MESSAGE = "message";
    private static final String VERIFIQUE = "Verifique os campos!";

    @Autowired
    private RoleService roleService;

    @GetMapping("/lista")
    public ModelAndView lista(RoleFilter roleFilter, HttpServletRequest httpServletRequest, @RequestParam(value = "page", required = false) Optional<Integer> page, @RequestParam(value = "size", required = false) Optional<Integer> size) {
        Pageable pageable = PageRequest.of(page.orElse(TelevisivoConfig.INITIAL_PAGE), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        Pagina<Role> pagina = new Pagina<>(roleService.listaComPaginacao(roleFilter, pageable), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE), httpServletRequest);
        ModelAndView modelAndView = new ModelAndView("/role/lista");
        modelAndView.addObject("pageSizes", TelevisivoConfig.PAGE_SIZES);
        modelAndView.addObject("size", size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        modelAndView.addObject("pagina", pagina);
        return modelAndView;
    }

    @GetMapping("/cadastro")
    public ModelAndView viewSalvar(Role role) {
        ModelAndView modelAndView = new ModelAndView("/role/role");
        modelAndView.addObject(ROLE, role);
        return modelAndView;
    }

    @GetMapping("/{id}/detalhes")
    public ModelAndView detalhes(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/role/detalhes");
        modelAndView.addObject(ROLE, roleService.getOne(id));
        return modelAndView;
    }

    @GetMapping("/{id}/alterar")
    public ModelAndView viewAlterar(@PathVariable("id") Long id) {
        return viewSalvar(roleService.getOne(id));
    }

    @GetMapping("/{id}/remover")
    public ModelAndView viewRemover(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/role/remover");
        modelAndView.addObject(ROLE, roleService.getOne(id));
        return modelAndView;
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Role role, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute(MESSAGE, VERIFIQUE);
            return "redirect:./cadastro";
        }
        roleService.save(role);
        attributes.addFlashAttribute(SUCCESS, "Registro adicionado com sucesso.");
        return "redirect:./lista";
    }

    @PostMapping("/{id}/alterar")
    public String alterar(@Valid Role role, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "redirect:./alterar";
        }
        roleService.update(role);
        attributes.addFlashAttribute(SUCCESS, "Registro alterado com sucesso.");
        return DETALHES;
    }

    @PostMapping("/{id}/remover")
    public String remover(@PathVariable("id") Long id, RedirectAttributes attributes) {
        roleService.deleteById(id);
        attributes.addFlashAttribute(SUCCESS, "Registro removido com sucesso.");
        return "redirect:../lista";
    }
    @PostMapping(value = { "/{id}/alterar", "/{id}/remover" }, params = "cancelar")
    public String cancelar() {
        return DETALHES;
    }

    @PostMapping(value = { "/salvar" }, params = "cancelar")
	public String cancelarCadastro() {
		return "redirect:./lista";
    }
}