package com.televisivo.controller;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value = "lista", method = RequestMethod.GET)
    public ModelAndView lista(RolePermissaoFilter rolePermissaoFilter) {
        ModelAndView modelAndView = new ModelAndView("/direitos/lista");
        List<RolePermissao> lista = rolePermissaoService.buscarRolePermissaoFilter(rolePermissaoFilter);
        modelAndView.addObject("lista", lista);
        return modelAndView;
    }

    @RequestMapping(value = "/adicionar", method = RequestMethod.GET)
    public ModelAndView cadastro(RolePermissao rolePermissao) {
        ModelAndView modelAndView = new ModelAndView("/direitos/direitos");
        modelAndView.addObject("rolePermissao", rolePermissao);
        return modelAndView;
    }

    @RequestMapping(value = "/adicionar", method = RequestMethod.POST)
    public ModelAndView adicionar(@Valid RolePermissao rolePermissao, RedirectAttributes attributes) {
        RolePermissaoId id = new RolePermissaoId();
        id.setRole(rolePermissao.getRole().getId());
        id.setPermissao(rolePermissao.getPermissao().getId());
        id.setEscopo(rolePermissao.getEscopo().getId());
        rolePermissao.setId(id);
        rolePermissaoService.adicionar(rolePermissao);
        attributes.addFlashAttribute("success", "Registro adicionado com sucesso.");
        return new ModelAndView("redirect:/rolePermissao/lista");
    }

    // @RequestMapping(value = "/remover", method = RequestMethod.POST)
    // public ModelAndView remover(RolePermissao rolePermissao, RedirectAttributes attributes) {
    //     rolePermissaoService.remover(rolePermissao);
    //     attributes.addFlashAttribute("success", "Registro removido com sucesso.");
    //     return new ModelAndView("redirect:/rolePermissao/lista");
    // }

    @RequestMapping(value = "/remover/{role_id}/{permissao_id}/{escopo_id}", method = RequestMethod.GET)
    public ModelAndView removerId(@PathVariable("role_id") Long role_id, @PathVariable("permissao_id") Long permissao_id, @PathVariable("escopo_id") Long escopo_id) {
        RolePermissaoId id = new RolePermissaoId();

        id.setRole(role_id);
        id.setPermissao(permissao_id);
        id.setEscopo(escopo_id);
        
        RolePermissao rolePermissao = rolePermissaoService.buscarId(id);
        ModelAndView modelAndView = new ModelAndView("/rolePermissao/remover");
        modelAndView.addObject("rolePermissao", rolePermissao);
        return modelAndView;
    }
    
    @RequestMapping(value = {"/adicionar", "/alterar", "/remover"}, method = RequestMethod.POST, params = "action=cancelar")
	public String cancelar() {
		return "redirect:/direitos/lista";
    }
    
    @ModelAttribute("roles")
    public List<Role> getRoles() {
        return roleService.listar();
    }

    @ModelAttribute("permissoes")
    public List<Permissao> getPermissoes() {
        return permissaoService.listar();
    }

    @ModelAttribute("escopos")
    public List<Escopo> getEscopos() {
        return escopoService.listar();
    }
}