package com.televisivo.web.controller;

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
@RequestMapping(value = "/serie/{id}/temporada")
public class TemporadaController {

    private static final String TEMPORADA = "temporada";
    private static final String SUCCESS = "success";
    private static final String REDIRECT_SERIE = "redirect:/serie/";
    private static final String HTML_TEMPORADA = "/temporada/temporada";

    @Autowired
    private TemporadaService temporadaService;

    @GetMapping("{id}")
    public ModelAndView detalhar(@PathVariable("id") Long id) {
        Temporada temporada = temporadaService.getOne(id);
        ModelAndView modelAndView = new ModelAndView("/temporada/detalhes");
        modelAndView.addObject(TEMPORADA, temporada);
        modelAndView.addObject("episodios", temporadaService.episodios(id));
        return modelAndView;
    }

    @GetMapping("/{id}/alterar")
    public ModelAndView viewAlterar(@PathVariable("id") Long id) {
        Temporada temporada = temporadaService.getOne(id);
        ModelAndView modelAndView = new ModelAndView(HTML_TEMPORADA);
        Episodio episodio = new Episodio();
        episodio.setTemporada(temporada);
        temporada.getEpisodios().add(episodio);
        modelAndView.addObject(TEMPORADA, temporada);
        return modelAndView;
    }
    
    @PostMapping("/{id}/alterar")
    public String alterar(@PathVariable("id") Long id, @Valid Temporada temporada, BindingResult result, RedirectAttributes attributes) {
        Long cod = temporadaService.findSerieByIdTemporada(id);
        if (result.hasErrors()) {
            attributes.addFlashAttribute("message", "Verifique os campos!");
            return REDIRECT_SERIE + cod + "/temporada/" + temporada.getId() + "/alterar";
        }
        temporadaService.update(temporada);
        temporadaService.salvarEpisodio(temporada);
        temporadaService.atualizarQtdEpisodios(id);
        attributes.addFlashAttribute(SUCCESS, "Registro alterado com sucesso.");
        return REDIRECT_SERIE + cod + "/temporada/" + temporada.getId();
    }

    @GetMapping(value = "/{id}/alterar", params = "addrow")
    public ModelAndView adicionarEpisodio(@PathVariable("id") Long id, Temporada temporada) {
        temporada = temporadaService.adicionarEpisodio(temporada);
        ModelAndView modelAndView = new ModelAndView(HTML_TEMPORADA);
        modelAndView.addObject(TEMPORADA, temporada);
        return modelAndView;
    }

    @GetMapping(value = "/{id}/alterar", params = "removerow")
    public ModelAndView removerEpisodio(@PathVariable("id") Long id, Temporada temporada, HttpServletRequest request) {
        int index = Integer.parseInt(request.getParameter("removerow"));
        temporada = temporadaService.removerEpisodio(temporada, index);
        ModelAndView modelAndView = new ModelAndView(HTML_TEMPORADA);
        temporadaService.atualizarQtdEpisodios(id);
        modelAndView.addObject(TEMPORADA, temporada);
        return modelAndView;
    }

    @GetMapping("/{id}/remover")
    public ModelAndView viewRemover(@PathVariable("id") Long id) {
        Temporada temporada = temporadaService.getOne(id);
        ModelAndView modelAndView = new ModelAndView("/temporada/remover");
        modelAndView.addObject(TEMPORADA, temporada);
        modelAndView.addObject("episodios", temporadaService.episodios(id));
        return modelAndView;
    }

    @PostMapping("/{id}/remover")
    public String remover(@PathVariable("id") Long id, RedirectAttributes attributes) {
        Long cod = temporadaService.findSerieByIdTemporada(id);
        temporadaService.deleteById(id);
        temporadaService.atualizarQtdTemporadas(cod);
        attributes.addFlashAttribute(SUCCESS, "Registro removido com sucesso.");
        return REDIRECT_SERIE + cod;
    }

    @PostMapping(value = { "", "/", "/{id}/alterar", "/{id}/remover" }, params = "cancelar")
	public String cancelar(@PathVariable("id") Long id) {
        Long idSerie = temporadaService.findSerieByIdTemporada(id);
		return REDIRECT_SERIE + idSerie + "/temporada/" + id;
    }
}