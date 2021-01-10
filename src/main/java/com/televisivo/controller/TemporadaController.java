package com.televisivo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.televisivo.model.Episodio;
import com.televisivo.model.Temporada;
import com.televisivo.service.TemporadaService;

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
@RequestMapping("/serie/{id}/temporada")
public class TemporadaController {

    private static final String TEMPORADA = "temporada";
    private static final String EPISODIOS = "episodios";
    private static final String EPISODIO = "episodio";
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private static final String REDIRECT_SERIE = "redirect:../../detalhes";
    private static final String REDIRECT_TEMPORADA = "redirect:./detalhes";
    private static final String HTML_TEMPORADA = "/temporada/temporada";

    @Autowired
    private TemporadaService temporadaService;

    @GetMapping("{id}/detalhes")
    public ModelAndView detalhar(@PathVariable("id") Long id) {
        Temporada temporada = temporadaService.getOne(id);
        ModelAndView modelAndView = new ModelAndView("/temporada/detalhes");
        modelAndView.addObject(TEMPORADA, temporada);
        modelAndView.addObject(EPISODIOS, temporadaService.episodios(id));
        return modelAndView;
    }

    @GetMapping("/{id}/alterar")
    public ModelAndView viewAlterar(@PathVariable("id") Long id) {
        Temporada temporada = temporadaService.getOne(id);
        ModelAndView modelAndView = new ModelAndView(HTML_TEMPORADA);
        modelAndView.addObject(TEMPORADA, temporada);
        modelAndView.addObject(EPISODIOS, temporadaService.episodios(id));
        return modelAndView;
    }

    @PostMapping("/{id}/alterar")
    public String alterar(@PathVariable("id") Long id, @Valid Temporada temporada, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute(FAIL, "Verifique os campos!");
            return "redirect:./alterar";
        }
        temporadaService.salvarEpisodio(temporada);
        temporadaService.update(temporada);
        attributes.addFlashAttribute(SUCCESS, "Temporada alterada com sucesso.");
        return REDIRECT_TEMPORADA;
    }

    @PostMapping(value = "/{id}/alterar", params = "addRow")
    public ModelAndView adicionarEpisodio(@PathVariable("id") Long id, Temporada temporada, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute(FAIL, "Verifique os campos!");
            return viewAlterar(id);
        }
        temporadaService.salvarEpisodio(temporada);
        ModelAndView modelAndView = new ModelAndView(HTML_TEMPORADA);
        modelAndView.addObject(TEMPORADA, temporadaService.adicionarEpisodio(temporada));
        modelAndView.addObject(EPISODIOS, temporadaService.episodios(id));
        return modelAndView;
    }

    @PostMapping(value = "/{id}/alterar", params = "removeRow")
    public ModelAndView removerEpisodio(@PathVariable("id") Long id, Temporada temporada, HttpServletRequest request) {
        Episodio episodio = temporadaService.findEpisodioByIdEpisodio(Long.parseLong(request.getParameter("removeRow")));
        temporadaService.removerEpisodio(episodio);
        ModelAndView modelAndView = new ModelAndView(HTML_TEMPORADA);
        modelAndView.addObject(TEMPORADA, episodio.getTemporada());
        return modelAndView;
    }

    @PostMapping(value = "/{id}/alterar", params = "duplicateRow")
    public ModelAndView duplicateRow(@PathVariable("id") Long id, Temporada temporada, HttpServletRequest request) {
        Episodio episodio = temporadaService.findEpisodioByIdEpisodio(Long.parseLong(request.getParameter("duplicateRow")));
        temporada = temporadaService.duplicateRow(temporada, episodio);
        ModelAndView modelAndView = new ModelAndView(HTML_TEMPORADA);
        modelAndView.addObject(TEMPORADA, temporada);
        modelAndView.addObject(EPISODIOS, temporadaService.episodios(id));
        modelAndView.addObject(EPISODIO, new Episodio());
        temporadaService.salvarEpisodio(temporada);
        return modelAndView;
    }

    @GetMapping("/{id}/remover")
    public ModelAndView viewRemover(@PathVariable("id") Long id) {
        Temporada temporada = temporadaService.getOne(id);
        ModelAndView modelAndView = new ModelAndView("/temporada/remover");
        modelAndView.addObject(TEMPORADA, temporada);
        modelAndView.addObject(EPISODIOS, temporadaService.episodios(id));
        return modelAndView;
    }

    @PostMapping("/{id}/remover")
    public String remover(@PathVariable("id") Long id, RedirectAttributes attributes) {
        temporadaService.deleteById(id);
        attributes.addFlashAttribute(SUCCESS, "Temporada removida com sucesso.");
        return REDIRECT_SERIE;
    }

    @PostMapping(value = { "/{id}/alterar", "/{id}/remover" }, params = "cancelar")
	public String cancelar() {
		return REDIRECT_TEMPORADA;
    }
}