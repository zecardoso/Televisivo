package com.televisivo.web.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.televisivo.config.TelevisivoConfig;
import com.televisivo.model.Escopo;
import com.televisivo.repository.filters.EscopoFilter;
import com.televisivo.repository.pagination.Pagina;
import com.televisivo.service.EscopoService;

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
@RequestMapping(value = "/escopo")
public class EscopoController {

    private static final String ESCOPO = "escopo";
    private static final String SUCCESS = "success";
    private static final String LISTA = "redirect:/escopo/lista";

    @Autowired
    private EscopoService escopoService;

    @GetMapping("/lista")
    public ModelAndView lista(EscopoFilter escopoFilter, HttpServletRequest httpServletRequest, @RequestParam(value = "page", required = false) Optional<Integer> page, @RequestParam(value = "size", required = false) Optional<Integer> size) {
        Pageable pageable = PageRequest.of(page.orElse(TelevisivoConfig.INITIAL_PAGE), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        Pagina<Escopo> pagina = new Pagina<>(escopoService.listaComPaginacao(escopoFilter, pageable), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE), httpServletRequest);
        ModelAndView modelAndView = new ModelAndView("/escopo/lista");
        modelAndView.addObject("pageSizes", TelevisivoConfig.PAGE_SIZES);
        modelAndView.addObject("size", size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        modelAndView.addObject("pagina", pagina);
        return modelAndView;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastro(Escopo escopo) {
        ModelAndView modelAndView = new ModelAndView("/escopo/escopo");
        modelAndView.addObject(ESCOPO, escopo);
        return modelAndView;
    }

    @GetMapping("/detalhes/{id}")
    public ModelAndView detalhes(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/escopo/detalhes");
        Escopo escopo = escopoService.getOne(id);
        modelAndView.addObject(ESCOPO, escopo);
        return modelAndView;
    }

    @GetMapping("/alterar/{id}")
    public ModelAndView buscar(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/escopo/escopo");
        Escopo escopo = escopoService.getOne(id);
        modelAndView.addObject(ESCOPO, escopo);
        return modelAndView;
    }

    @GetMapping("/remover/{id}")
    public ModelAndView removerId(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/escopo/remover");
        Escopo escopo = escopoService.getOne(id);
        modelAndView.addObject(ESCOPO, escopo);
        return modelAndView;
    }

    @PostMapping(value = "/adicionar")
    public ModelAndView adicionar(@Valid Escopo escopo, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return cadastro(escopo);
        }
        escopoService.save(escopo);
        attributes.addFlashAttribute(SUCCESS, "Registro adicionado com sucesso.");
        return new ModelAndView(LISTA);
    }

    @PostMapping(value = "/alterar")
    public ModelAndView alterar(@Valid Escopo escopo, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return cadastro(escopo);
        }
        escopoService.update(escopo);
        attributes.addFlashAttribute(SUCCESS, "Registro alterado com sucesso.");
        return new ModelAndView(LISTA);
    }

    @PostMapping(value = "/remover")
    public ModelAndView remover(Escopo escopo, BindingResult result, RedirectAttributes attributes) {
        escopoService.deleteById(escopo.getId());
        attributes.addFlashAttribute(SUCCESS, "Registro removido com sucesso.");
        return new ModelAndView(LISTA);
    }

    @PostMapping(value = { "/adicionar", "/alterar", "/remover" }, params = "action=cancelar")
	public String cancelar() {
		return LISTA;
	}
}