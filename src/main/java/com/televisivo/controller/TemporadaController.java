package com.televisivo.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.televisivo.config.TelevisivoConfig;
import com.televisivo.controller.page.PaginaWrapper;
import com.televisivo.model.Temporada;
import com.televisivo.repository.filters.TemporadaFilter;
import com.televisivo.service.TemporadaService;
import com.televisivo.service.exceptions.EmailExistente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/temporada")
public class TemporadaController {

    @Autowired
    private TemporadaService temporadaService;

    // @GetMapping("/lista")
    // public ModelAndView lista(TemporadaFilter temporadaFilter, HttpServletRequest httpServletRequest, @RequestParam(value = "page", required = false) Optional<Integer> page, @RequestParam(value = "size", required = false) Optional<Integer> size) {
    //     Pageable pageable = PageRequest.of(page.orElse(TelevisivoConfig.INITIAL_PAGE), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
    //     PaginaWrapper<Temporada> paginaWrapper = new PaginaWrapper(temporadaService.listaComPaginacao(temporadaFilter, pageable), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE), httpServletRequest);
    //     ModelAndView modelAndView = new ModelAndView("/temporada/lista");
    //     modelAndView.addObject("pageSizes", TelevisivoConfig.PAGE_SIZES);
    //     modelAndView.addObject("size", size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
    //     modelAndView.addObject("pagina", paginaWrapper);
    //     return modelAndView;
    // }

    @GetMapping("/cadastro")
    public ModelAndView cadastro(Temporada temporada) {
        ModelAndView modelAndView = new ModelAndView("/temporada/temporada");
        modelAndView.addObject("temporada", temporada);
        return modelAndView;
    }

    @GetMapping("/detalhes/{id}")
    public ModelAndView detalhes(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/temporada/detalhes");
        Temporada temporada = temporadaService.getOne(id);
        modelAndView.addObject("temporada", temporada);
        return modelAndView;
    }

    @GetMapping("/alterar/{id}")
    public ModelAndView alterarId(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/temporada/temporada");
        Temporada temporada = temporadaService.buscarPorIdEpisodio(id);
        modelAndView.addObject("temporada", temporada);
        return modelAndView;
    }

    @RequestMapping(value = "/remover/{id}", method = RequestMethod.GET)
    public ModelAndView removerId(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/temporada/remover");
        Temporada temporada = temporadaService.getOne(id);
        modelAndView.addObject("temporada", temporada);
        return modelAndView;
    }

    // @RequestMapping(value = "/adicionar", method = RequestMethod.POST)
    // public ModelAndView adicionar(@Valid Temporada temporada, BindingResult result, RedirectAttributes attributes) {
    //     if (result.hasErrors()) {
    //         return cadastro(temporada);
    //     }
    //     temporadaService.save(temporada);
    //     attributes.addFlashAttribute("success", "Registro adicionado com sucesso.");
    //     return new ModelAndView("redirect:/temporada/lista");
    // }

    @RequestMapping(value = "/alterar", method = RequestMethod.POST)
    public ModelAndView alterar(@Valid Temporada temporada, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return cadastro(temporada);
        }
        temporadaService.update(temporada);
        temporadaService.salvarEpisodio(temporada);
        attributes.addFlashAttribute("success", "Registro alterado com sucesso.");
        return cadastro(temporada);
    }

    @RequestMapping(value = "/remover", method = RequestMethod.POST)
    public ModelAndView remover(Temporada temporada, BindingResult result, RedirectAttributes attributes) {
        temporadaService.deleteById(temporada.getId());
        attributes.addFlashAttribute("success", "Registro removido com sucesso.");
        return cadastro(temporada);
    }

    @RequestMapping(value = "/alterar", method = RequestMethod.GET, params = "addrow")
    public ModelAndView adicionarEpisodio(@Valid Temporada temporada, BindingResult result, RedirectAttributes attributes, Model model) {
        ModelAndView modelAndView = new ModelAndView("/temporada/temporada");
        temporada = temporadaService.adicionarEpisodio(temporada);
        modelAndView.addObject("temporada", temporada);
        return modelAndView;
    }

    @RequestMapping(value = "/alterar", method = RequestMethod.GET, params = "removerow")
    public ModelAndView removerEpisodio(@Valid Temporada temporada, BindingResult result, RedirectAttributes attributes, HttpServletRequest request) {
        int index = Integer.valueOf(request.getParameter("removerow"));
        temporada = temporadaService.removerEpisodio(temporada, index);
        ModelAndView modelAndView = new ModelAndView("/temporada/temporada");
        modelAndView.addObject("temporada", temporada);
        return modelAndView;
    }

    // @RequestMapping(value = {"/adicionar", "/alterar", "/remover"}, method = RequestMethod.POST, params = "action=cancelar")
	// public String cancelar() {
	// 	return "redirect:/temporada/lista";
	// }
}