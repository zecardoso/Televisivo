package com.televisivo.web.controller;

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
@RequestMapping(value = "/permissao")
public class PermissaoController {

    private static final String ESCOPO = "permissao";
    private static final String SUCCESS = "success";
    private static final String LISTA = "redirect:/permissao/lista";

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
    public ModelAndView cadastro(Permissao permissao) {
        ModelAndView modelAndView = new ModelAndView("/permissao/permissao");
        modelAndView.addObject(ESCOPO, permissao);
        return modelAndView;
    }

    @GetMapping("/detalhes/{id}")
    public ModelAndView detalhes(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/permissao/detalhes");
        Permissao permissao = permissaoService.getOne(id);
        modelAndView.addObject(ESCOPO, permissao);
        return modelAndView;
    }

    @GetMapping("/alterar/{id}")
    public ModelAndView buscar(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/permissao/permissao");
        Permissao permissao = permissaoService.getOne(id);
        modelAndView.addObject(ESCOPO, permissao);
        return modelAndView;
    }

    @GetMapping("/remover/{id}")
    public ModelAndView removerId(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/permissao/remover");
        Permissao permissao = permissaoService.getOne(id);
        modelAndView.addObject(ESCOPO, permissao);
        return modelAndView;
    }

    @PostMapping(value = "/adicionar")
    public ModelAndView adicionar(@Valid Permissao permissao, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return cadastro(permissao);
        }
        permissaoService.save(permissao);
        attributes.addFlashAttribute(SUCCESS, "Registro adicionado com sucesso.");
        return new ModelAndView(LISTA);
    }

    @PostMapping(value = "/alterar")
    public ModelAndView alterar(@Valid Permissao permissao, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return cadastro(permissao);
        }
        permissaoService.update(permissao);
        attributes.addFlashAttribute(SUCCESS, "Registro alterado com sucesso.");
        return new ModelAndView(LISTA);
    }

    @PostMapping(value = "/remover")
    public ModelAndView remover(Permissao permissao, BindingResult result, RedirectAttributes attributes) {
        permissaoService.deleteById(permissao.getId());
        attributes.addFlashAttribute(SUCCESS, "Registro removido com sucesso.");
        return new ModelAndView(LISTA);
    }

    @PostMapping(value = { "/adicionar", "/alterar", "/remover" }, params = "action=cancelar")
	public String cancelar() {
		return LISTA;
	}
}