package com.televisivo.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.televisivo.config.TelevisivoConfig;
import com.televisivo.model.Permissao;
import com.televisivo.repository.filters.PermissaoFilter;
import com.televisivo.repository.pagination.Pagina;
import com.televisivo.service.PermissaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("admin/permissao")
public class PermissaoController {

    private static final String PERMISSAO = "permissao";
    private static final String SUCCESS = "success";
    private static final String DETALHES = "redirect:./detalhes";
    private static final String FAIL = "fail";

    @Autowired
    private PermissaoService permissaoService;

    @GetMapping("/lista")
    public ModelAndView lista(PermissaoFilter permissaoFilter, HttpServletRequest httpServletRequest, @RequestParam(value = "page", required = false) Optional<Integer> page, @RequestParam(value = "size", required = false) Optional<Integer> size) {
        Pageable pageable = PageRequest.of(page.orElse(TelevisivoConfig.INITIAL_PAGE), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        Pagina<Permissao> pagina = new Pagina<>(permissaoService.listaComPaginacao(permissaoFilter, pageable), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE), httpServletRequest);
        ModelAndView modelAndView = new ModelAndView("/permissao/lista");
        modelAndView.addObject("pageSizes", TelevisivoConfig.PAGE_SIZES);
        modelAndView.addObject("size", size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        modelAndView.addObject("pagina", pagina);
        return modelAndView;
    }

    @GetMapping("/cadastro")
    public ModelAndView viewSalvar(Permissao permissao) {
        ModelAndView modelAndView = new ModelAndView("/permissao/permissao");
        modelAndView.addObject(PERMISSAO, permissao);
        return modelAndView;
    }

    @GetMapping("/{id}/detalhes")
    public ModelAndView detalhes(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/permissao/detalhes");
        modelAndView.addObject(PERMISSAO, permissaoService.getOne(id));
        return modelAndView;
    }

    @GetMapping("/{id}/alterar")
    public ModelAndView viewAlterar(@PathVariable("id") Long id) {
        return viewSalvar(permissaoService.getOne(id));
    }

    @GetMapping("/{id}/remover")
    @PreAuthorize("hasPermission('PERMISSAO','INSEXCLUIRERIR')")
    public ModelAndView viewRemover(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/permissao/remover");
        modelAndView.addObject(PERMISSAO, permissaoService.getOne(id));
        return modelAndView;
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Permissao permissao, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute(FAIL, "Verifique os campos!");
            return "redirect:./cadastro";
        }
        permissaoService.save(permissao);
        attributes.addFlashAttribute(SUCCESS, "Registro adicionado.");
        return "redirect:./lista";
    }

    @PostMapping("/{id}/alterar")
    public String alterar(@Valid Permissao permissao, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute(FAIL, "Verifique os campos!");
            return "redirect:./alterar";
        }
        permissaoService.update(permissao);
        attributes.addFlashAttribute(SUCCESS, "Registro alterado.");
        return DETALHES;
    }

    @PostMapping("/{id}/remover")
    public String remover(@PathVariable("id") Long id, RedirectAttributes attributes) {
        permissaoService.deleteById(id);
        attributes.addFlashAttribute(SUCCESS, "Registro removido.");
        return "redirect:../lista";
    }

    @PostMapping(value = { "/{id}/alterar", "/{id}/remover" }, params = "cancelar")
    public String cancelar() {
        return DETALHES;
    }

    @PostMapping(value = "/salvar", params = "cancelar")
	public String cancelarCadastro() {
		return "redirect:./lista";
    }
}