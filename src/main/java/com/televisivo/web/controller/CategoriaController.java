package com.televisivo.web.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.televisivo.config.TelevisivoConfig;
import com.televisivo.model.Categoria;
import com.televisivo.repository.filters.CategoriaFilter;
import com.televisivo.repository.pagination.Pagina;
import com.televisivo.service.CategoriaService;

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
@RequestMapping(value = "/categoria")
public class CategoriaController {

    private static final String CATEGORIA = "categoria";
    private static final String SUCCESS = "success";
    private static final String LISTA = "redirect:/categoria/lista";

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/lista")
    public ModelAndView lista(CategoriaFilter categoriaFilter, HttpServletRequest httpServletRequest, @RequestParam(value = "page", required = false) Optional<Integer> page, @RequestParam(value = "size", required = false) Optional<Integer> size) {
        Pageable pageable = PageRequest.of(page.orElse(TelevisivoConfig.INITIAL_PAGE), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        Pagina<Categoria> pagina = new Pagina<>(categoriaService.listaComPaginacao(categoriaFilter, pageable), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE), httpServletRequest);
        ModelAndView modelAndView = new ModelAndView("/categoria/lista");
        modelAndView.addObject("pageSizes", TelevisivoConfig.PAGE_SIZES);
        modelAndView.addObject("size", size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        modelAndView.addObject("pagina", pagina);
        return modelAndView;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastro(Categoria categoria) {
        ModelAndView modelAndView = new ModelAndView("/categoria/categoria");
        modelAndView.addObject(CATEGORIA, categoria);
        return modelAndView;
    }

    @GetMapping("/detalhes/{id}")
    public ModelAndView detalhes(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/categoria/detalhes");
        Categoria categoria = categoriaService.getOne(id);
        modelAndView.addObject(CATEGORIA, categoria);
        return modelAndView;
    }

    @GetMapping("/alterar/{id}")
    public ModelAndView buscar(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/categoria/categoria");
        Categoria categoria = categoriaService.getOne(id);
        modelAndView.addObject(CATEGORIA, categoria);
        return modelAndView;
    }

    @GetMapping("/remover/{id}")
    public ModelAndView removerId(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/categoria/remover");
        Categoria categoria = categoriaService.getOne(id);
        modelAndView.addObject(CATEGORIA, categoria);
        return modelAndView;
    }

    @PostMapping(value = "/adicionar")
    public ModelAndView adicionar(@Valid Categoria categoria, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return cadastro(categoria);
        }
        categoriaService.save(categoria);
        attributes.addFlashAttribute(SUCCESS, "Registro adicionado com sucesso.");
        return new ModelAndView(LISTA);
    }

    @PostMapping(value = "/alterar")
    public ModelAndView alterar(@Valid Categoria categoria, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return cadastro(categoria);
        }
        categoriaService.update(categoria);
        attributes.addFlashAttribute(SUCCESS, "Registro alterado com sucesso.");
        return new ModelAndView(LISTA);
    }

    @PostMapping(value = "/remover")
    public ModelAndView remover(Categoria categoria, BindingResult result, RedirectAttributes attributes) {
        categoriaService.deleteById(categoria.getId());
        attributes.addFlashAttribute(SUCCESS, "Registro removido com sucesso.");
        return new ModelAndView(LISTA);
    }

    @PostMapping(value = { "/adicionar", "/alterar", "/remover" }, params = "action=cancelar")
    public String cancelar() {
        return LISTA;
    }
}