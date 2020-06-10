package com.televisivo.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.televisivo.model.Temporada;
import com.televisivo.service.TemporadaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/serie/temporada")
public class TemporadaController {

    private static final String TEMPORADA = "temporada";
    private static final String SUCCESS = "success";
    private static final String HTML_TEMPORADA = "/temporada/temporada";

    @Autowired
    private TemporadaService temporadaService;

    @GetMapping("/cadastro")
    public ModelAndView cadastro(Temporada temporada) {
        ModelAndView modelAndView = new ModelAndView(HTML_TEMPORADA);
        modelAndView.addObject(TEMPORADA, temporada);
        return modelAndView;
    }

    @GetMapping("/detalhes/{id}")
    public ModelAndView detalhes(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/temporada/detalhes");
        Temporada temporada = temporadaService.getOne(id);
        modelAndView.addObject(TEMPORADA, temporada);
        return modelAndView;
    }

    @GetMapping("/alterar/{id}")
    public ModelAndView alterarId(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView(HTML_TEMPORADA);
        Temporada temporada = temporadaService.buscarPorIdEpisodio(id);
        modelAndView.addObject(TEMPORADA, temporada);
        return modelAndView;
    }

    @GetMapping("/remover/{id}")
    public ModelAndView removerId(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/temporada/remover");
        Temporada temporada = temporadaService.getOne(id);
        modelAndView.addObject(TEMPORADA, temporada);
        return modelAndView;
    }
    
    @PostMapping("/alterar")
    public ModelAndView alterar(@Valid Temporada temporada, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return cadastro(temporada);
        }
        temporadaService.update(temporada);
        temporadaService.salvarEpisodio(temporada);
        attributes.addFlashAttribute(SUCCESS, "Registro alterado com sucesso.");
        return cadastro(temporada);
    }

    @PostMapping("/remover")
    public ModelAndView remover(Temporada temporada, BindingResult result, RedirectAttributes attributes) {
        temporadaService.deleteById(temporada.getId());
        attributes.addFlashAttribute(SUCCESS, "Registro removido com sucesso.");
        return cadastro(temporada);
    }

    @GetMapping(value = "/alterar", params = "addrow")
    public ModelAndView adicionarEpisodio(@Valid Temporada temporada, BindingResult result, RedirectAttributes attributes, Model model) {
        ModelAndView modelAndView = new ModelAndView(HTML_TEMPORADA);
        temporada = temporadaService.adicionarEpisodio(temporada);
        modelAndView.addObject(TEMPORADA, temporada);
        return modelAndView;
    }

    @GetMapping(value = "/alterar", params = "removerow")
    public ModelAndView removerEpisodio(@Valid Temporada temporada, BindingResult result, RedirectAttributes attributes, HttpServletRequest request) {
        int index = Integer.parseInt(request.getParameter("removerow"));
        temporada = temporadaService.removerEpisodio(temporada, index);
        ModelAndView modelAndView = new ModelAndView(HTML_TEMPORADA);
        modelAndView.addObject(TEMPORADA, temporada);
        return modelAndView;
    }
}