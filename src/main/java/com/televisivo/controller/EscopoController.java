package com.televisivo.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.televisivo.config.TelevisivoConfig;
import com.televisivo.controller.page.PaginaWrapper;
import com.televisivo.model.Escopo;
import com.televisivo.repository.filters.EscopoFilter;
import com.televisivo.service.EscopoService;

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
@RequestMapping(value = "/escopo")
public class EscopoController {

    @Autowired
    private EscopoService escopoService;

    @GetMapping("/lista")
    public ModelAndView lista(EscopoFilter escopoFilter, HttpServletRequest httpServletRequest, @RequestParam(value = "page", required = false) Optional<Integer> page, @RequestParam(value = "size", required = false) Optional<Integer> size) {
        Pageable pageable = PageRequest.of(page.orElse(TelevisivoConfig.INITIAL_PAGE), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        PaginaWrapper<Escopo> paginaWrapper = new PaginaWrapper(escopoService.listaComPaginacao(escopoFilter, pageable), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE), httpServletRequest);
        ModelAndView modelAndView = new ModelAndView("/escopo/lista");
        modelAndView.addObject("pageSizes", TelevisivoConfig.PAGE_SIZES);
        modelAndView.addObject("size", size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        modelAndView.addObject("pagina", paginaWrapper);
        return modelAndView;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastro(Escopo escopo) {
        ModelAndView modelAndView = new ModelAndView("/escopo/escopo");
        modelAndView.addObject("escopo", escopo);
        return modelAndView;
    }

    @GetMapping("/detalhes/{id}")
    public ModelAndView detalhes(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/escopo/detalhes");
        Escopo escopo = escopoService.getOne(id);
        modelAndView.addObject("escopo", escopo);
        return modelAndView;
    }

    @GetMapping("/alterar/{id}")
    public ModelAndView buscar(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/escopo/escopo");
        Escopo escopo = escopoService.getOne(id);
        modelAndView.addObject("escopo", escopo);
        return modelAndView;
    }

    @GetMapping("/remover/{id}")
    public ModelAndView removerId(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/escopo/remover");
        Escopo escopo = escopoService.getOne(id);
        modelAndView.addObject("escopo", escopo);
        return modelAndView;
    }

    @RequestMapping(value = "/adicionar", method = RequestMethod.POST)
    public ModelAndView adicionar(@Valid Escopo escopo, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return cadastro(escopo);
        }
        escopoService.save(escopo);
        attributes.addFlashAttribute("success", "Registro adicionado com sucesso.");
        return new ModelAndView("redirect:/escopo/lista");
    }

    @RequestMapping(value = "/alterar", method = RequestMethod.POST)
    public ModelAndView alterar(@Valid Escopo escopo, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return cadastro(escopo);
        }
        escopoService.update(escopo);
        attributes.addFlashAttribute("success", "Registro alterado com sucesso.");
        return new ModelAndView("redirect:/escopo/lista");
    }

    @RequestMapping(value = "/remover", method = RequestMethod.POST)
    public ModelAndView remover(Escopo escopo, BindingResult result, RedirectAttributes attributes) {
        escopoService.deleteById(escopo.getId());
        attributes.addFlashAttribute("success", "Registro removido com sucesso.");
        return new ModelAndView("redirect:/escopo/lista");
    }

    @RequestMapping(value = {"/adicionar", "/alterar", "/remover"}, method = RequestMethod.POST, params = "action=cancelar")
	public String cancelar() {
		return "redirect:/escopo/lista";
	}
}