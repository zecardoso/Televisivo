package com.televisivo.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.televisivo.config.TelevisivoConfig;
import com.televisivo.controller.page.PaginaWrapper;
import com.televisivo.model.Episodio;
import com.televisivo.repository.filters.EpisodioFilter;
import com.televisivo.service.EpisodioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/episodio")
public class EpisodioController {

    @Autowired
    private EpisodioService episodioService;

    @GetMapping("/lista")
    public ModelAndView lista(EpisodioFilter episodioFilter, HttpServletRequest httpServletRequest, @RequestParam(value = "page", required = false) Optional<Integer> page, @RequestParam(value = "size", required = false) Optional<Integer> size) {
        Pageable pageable = PageRequest.of(page.orElse(TelevisivoConfig.INITIAL_PAGE), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        PaginaWrapper<Episodio> paginaWrapper = new PaginaWrapper(episodioService.listaComPaginacao(episodioFilter, pageable), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE), httpServletRequest);
        ModelAndView modelAndView = new ModelAndView("/episodio/lista");
        modelAndView.addObject("pageSizes", TelevisivoConfig.PAGE_SIZES);
        modelAndView.addObject("size", size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        modelAndView.addObject("pagina", paginaWrapper);
        return modelAndView;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastro(Episodio episodio) {
        ModelAndView modelAndView = new ModelAndView("/episodio/episodio");
        modelAndView.addObject("episodio", episodio);
        return modelAndView;
    }

    @GetMapping("/detalhes/{id}")
    public ModelAndView detalhes(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/episodio/detalhes");
        Episodio episodio = episodioService.getOne(id);
        modelAndView.addObject("episodio", episodio);
        return modelAndView;
    }

    @GetMapping("/alterar/{id}")
    public ModelAndView buscar(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/episodio/episodio");
        Episodio episodio = episodioService.getOne(id);
        modelAndView.addObject("episodio", episodio);
        return modelAndView;
    }

    @GetMapping("/remover/{id}")
    public ModelAndView removerId(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/episodio/remover");
        Episodio episodio = episodioService.getOne(id);
        modelAndView.addObject("episodio", episodio);
        return modelAndView;
    }

    @RequestMapping(value = "/adicionar", method = RequestMethod.POST)
    public ModelAndView adicionar(@Valid Episodio episodio, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return cadastro(episodio);
        }
        episodioService.save(episodio);
        attributes.addFlashAttribute("success", "Registro adicionado com sucesso.");
        return new ModelAndView("redirect:/episodio/lista");
    }

    @RequestMapping(value = "/alterar", method = RequestMethod.POST)
    public ModelAndView alterar(@Valid Episodio episodio, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return cadastro(episodio);
        }
        episodioService.update(episodio);
        attributes.addFlashAttribute("success", "Registro alterado com sucesso.");
        return new ModelAndView("redirect:/episodio/lista");
    }

    @RequestMapping(value = "/remover", method = RequestMethod.POST)
    public ModelAndView remover(Episodio episodio, BindingResult result, RedirectAttributes attributes) {
        episodioService.deleteById(episodio.getId());
        attributes.addFlashAttribute("success", "Registro removido com sucesso.");
        return new ModelAndView("redirect:/episodio/lista");
    }

    @RequestMapping(value = {"/adicionar", "/alterar", "/remover"}, method = RequestMethod.POST, params = "action=cancelar")
	public String cancelar() {
		return "redirect:/episodio/lista";
	}
}