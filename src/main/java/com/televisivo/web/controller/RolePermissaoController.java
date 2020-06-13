package com.televisivo.web.controller;

import java.util.List;

import javax.validation.Valid;

import com.televisivo.model.Escopo;
import com.televisivo.model.Permissao;
import com.televisivo.model.Role;
import com.televisivo.model.RolePermissao;
import com.televisivo.model.RolePermissaoId;
import com.televisivo.repository.filters.RolePermissaoFilter;
import com.televisivo.service.EscopoService;
import com.televisivo.service.PermissaoService;
import com.televisivo.service.RolePermissaoService;
import com.televisivo.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/direitos")
public class RolePermissaoController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissaoService permissaoService;
    
    @Autowired
    private EscopoService escopoService;

    @Autowired
    private RolePermissaoService rolePermissaoService;

    @GetMapping("/lista")
    public ModelAndView lista(RolePermissaoFilter rolePermissaoFilter) {
        ModelAndView modelAndView = new ModelAndView("/direitos/lista");
        List<RolePermissao> lista = rolePermissaoService.findRolePermissaoEscopoFilter(rolePermissaoFilter);
        modelAndView.addObject("lista", lista);
        return modelAndView;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastro(RolePermissao rolePermissao) {
        ModelAndView modelAndView = new ModelAndView("/direitos/direitos");
        modelAndView.addObject("rolePermissao", rolePermissao);
        return modelAndView;
    }

    @PostMapping("/adicionar")
    public ModelAndView adicionar(@Valid RolePermissao rolePermissao, RedirectAttributes attributes) {
        RolePermissaoId id = new RolePermissaoId();
        id.setRole(rolePermissao.getRole().getId());
        id.setPermissao(rolePermissao.getPermissao().getId());
        id.setEscopo(rolePermissao.getEscopo().getId());
        rolePermissao.setId(id);
        rolePermissaoService.save(rolePermissao);
        attributes.addFlashAttribute("success", "Registro adicionado com sucesso.");
        return new ModelAndView("redirect:/direitos/lista");
    }

    @GetMapping("/remover/{roleId}/{permissaoId}/{escopoId}")
    public ModelAndView removerId(@PathVariable("roleId") Long roleId, @PathVariable("permissaoId") Long permissaoId, @PathVariable("escopoId") Long escopoId) {
        RolePermissaoId id = new RolePermissaoId();
        id.setRole(roleId);
        id.setPermissao(permissaoId);
        id.setEscopo(escopoId);
        RolePermissao rolePermissao = rolePermissaoService.getOne(id);
        ModelAndView modelAndView = new ModelAndView("/direitos/remover");
        modelAndView.addObject("rolePermissao", rolePermissao);
        return modelAndView;
    }
    
    @PostMapping(value = "/remover")
    public ModelAndView remover(RolePermissao rolePermissao, BindingResult result, RedirectAttributes attributes) {
        rolePermissaoService.deleteById(rolePermissao.getId());
        attributes.addFlashAttribute("success", "Registro removido com sucesso.");
        return new ModelAndView("/direitos/lista");
    }

    @ModelAttribute("roles")
    public List<Role> getRoles() {
        return roleService.findAll();
    }

    @ModelAttribute("permissoes")
    public List<Permissao> getPermissoes() {
        return permissaoService.findAll();
    }

    @ModelAttribute("escopos")
    public List<Escopo> getEscopos() {
        return escopoService.findAll();
    }

    @PostMapping(value = { "/adicionar", "/alterar", "/remover" }, params = "action=cancelar")
	public String cancelar() {
		return "redirect:/direitos/lista";
    }
}