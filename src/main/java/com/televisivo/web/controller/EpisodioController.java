package com.televisivo.web.controller;

import javax.validation.Valid;

import com.televisivo.model.Episodio;
import com.televisivo.service.EpisodioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/serie/temporada/episodio")
public class EpisodioController {

    private static final String EPISODIO = "episodio";

    @Autowired
    private EpisodioService episodioService;
    
    @GetMapping("/cadastro")
    public ModelAndView cadastro(Episodio episodio) {
        ModelAndView modelAndView = new ModelAndView("/episodio/episodio");
        modelAndView.addObject(EPISODIO, episodio);
        return modelAndView;
    }

    @GetMapping("/detalhes/{id}")
    public ModelAndView detalhes(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/episodio/detalhes");
        Episodio episodio = episodioService.getOne(id);
        modelAndView.addObject(EPISODIO, episodio);
        return modelAndView;
    }

    @GetMapping("/alterar/{id}")
    public ModelAndView buscar(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/episodio/episodio");
        Episodio episodio = episodioService.getOne(id);
        modelAndView.addObject(EPISODIO, episodio);
        return modelAndView;
    }

    @GetMapping("/remover/{id}")
    public ModelAndView removerId(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/episodio/remover");
        Episodio episodio = episodioService.getOne(id);
        modelAndView.addObject(EPISODIO, episodio);
        return modelAndView;
    }

    @PostMapping(value = "/alterar")
    public ModelAndView alterar(@Valid Episodio episodio, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return cadastro(episodio);
        }
        episodioService.update(episodio);
        attributes.addFlashAttribute("success", "Registro alterado com sucesso.");
        return new ModelAndView("redirect:/episodio/lista");
    }

    @PostMapping(value = "/remover")
    public ModelAndView remover(Episodio episodio, BindingResult result, RedirectAttributes attributes) {
        episodioService.deleteById(episodio.getId());
        attributes.addFlashAttribute("success", "Registro removido com sucesso.");
        return new ModelAndView("redirect:/episodio/lista");
    }
}