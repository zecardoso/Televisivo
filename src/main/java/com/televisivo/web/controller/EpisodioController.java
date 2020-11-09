package com.televisivo.web.controller;

import javax.validation.Valid;

import com.televisivo.model.Episodio;
import com.televisivo.model.Temporada;
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
@RequestMapping(value = "/serie/{id}/temporada/{id}/episodio")
public class EpisodioController {

    private static final String EPISODIO = "episodio";
    private static final String REDIRECT_TEMPORADA = "redirect:../.././detalhes";
    private static final String DETALHES = "redirect:./detalhes";
    private static final String HTML_EPISODIO = "/episodio/episodio";

    @Autowired
    private EpisodioService episodioService;

    @GetMapping("{id}/detalhes")
    public ModelAndView detalhes(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/episodio/detalhes");
        Episodio episodio = episodioService.getOne(id);
        modelAndView.addObject(EPISODIO, episodio);
        return modelAndView;
    }

    @GetMapping("/{id}/alterar")
    public ModelAndView viewAlterar(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView(HTML_EPISODIO);
        Episodio episodio = episodioService.getOne(id);
        modelAndView.addObject(EPISODIO, episodio);
        return modelAndView;
    }

    @PostMapping("/{id}/alterar")
    public String alterar(@PathVariable("id") Long id, @Valid Episodio episodio, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "redirect:../.././episodio/" + id + "/alterar";
        }
        episodioService.update(episodio);
        attributes.addFlashAttribute("success", "Registro alterado com sucesso.");
        return DETALHES;
    }

    @GetMapping("/{id}/remover")
    public ModelAndView viewRemover(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/episodio/remover");
        Episodio episodio = episodioService.getOne(id);
        modelAndView.addObject(EPISODIO, episodio);
        return modelAndView;
    }

    @PostMapping("/{id}/remover")
    public String remover(@PathVariable("id") Long id, RedirectAttributes attributes) {
        Temporada temporada = episodioService.findTemporadaByIdEpisodio(id);
        episodioService.deleteById(id);
        episodioService.atualizarQtdEpisodios(temporada);
        attributes.addFlashAttribute("success", "Registro removido com sucesso.");
        return REDIRECT_TEMPORADA;
    }

    @PostMapping(value = { "/{id}/alterar", "/{id}/remover" }, params = "cancelar")
	public String cancelar(@PathVariable("id") Long id) {
		return DETALHES;
    }
}