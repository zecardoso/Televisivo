package com.televisivo.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.televisivo.config.TelevisivoConfig;
import com.televisivo.controller.page.PaginaWrapper;
import com.televisivo.model.Elenco;
import com.televisivo.repository.filters.ElencoFilter;
import com.televisivo.service.ElencoService;

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
@RequestMapping(value = "/elenco")
public class ElencoController {

    @Autowired
    private ElencoService elencoService;

    @GetMapping("/lista")
    public ModelAndView lista(ElencoFilter elencoFilter, HttpServletRequest httpServletRequest, @RequestParam(value = "page", required = false) Optional<Integer> page, @RequestParam(value = "size", required = false) Optional<Integer> size) {
        Pageable pageable = PageRequest.of(page.orElse(TelevisivoConfig.INITIAL_PAGE), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        PaginaWrapper<Elenco> paginaWrapper = new PaginaWrapper(elencoService.listaComPaginacao(elencoFilter, pageable), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE), httpServletRequest);
        ModelAndView modelAndView = new ModelAndView("/elenco/lista");
        modelAndView.addObject("pageSizes", TelevisivoConfig.PAGE_SIZES);
        modelAndView.addObject("size", size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        modelAndView.addObject("pagina", paginaWrapper);
        return modelAndView;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastro(Elenco elenco) {
        ModelAndView modelAndView = new ModelAndView("/elenco/elenco");
        modelAndView.addObject("elenco", elenco);
        return modelAndView;
    }

    @GetMapping("/detalhes/{id}")
    public ModelAndView detalhes(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/elenco/detalhes");
        Elenco elenco = elencoService.getOne(id);
        modelAndView.addObject("elenco", elenco);
        return modelAndView;
    }

    @GetMapping("/alterar/{id}")
    public ModelAndView buscar(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/elenco/elenco");
        Elenco elenco = elencoService.getOne(id);
        modelAndView.addObject("elenco", elenco);
        return modelAndView;
    }

    @GetMapping("/remover/{id}")
    public ModelAndView removerId(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/elenco/remover");
        Elenco elenco = elencoService.getOne(id);
        modelAndView.addObject("elenco", elenco);
        return modelAndView;
    }

    @RequestMapping(value = "/adicionar", method = RequestMethod.POST)
    public ModelAndView adicionar(@Valid Elenco elenco, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return cadastro(elenco);
        }
        elencoService.save(elenco);
        attributes.addFlashAttribute("success", "Registro adicionado com sucesso.");
        return new ModelAndView("redirect:/elenco/lista");
    }

    @RequestMapping(value = "/alterar", method = RequestMethod.POST)
    public ModelAndView alterar(@Valid Elenco elenco, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return cadastro(elenco);
        }
        elencoService.update(elenco);
        attributes.addFlashAttribute("success", "Registro alterado com sucesso.");
        return new ModelAndView("redirect:/elenco/lista");
    }

    @RequestMapping(value = "/remover", method = RequestMethod.POST)
    public ModelAndView remover(Elenco elenco, BindingResult result, RedirectAttributes attributes) {
        elencoService.deleteById(elenco.getId());
        attributes.addFlashAttribute("success", "Registro removido com sucesso.");
        return new ModelAndView("redirect:/elenco/lista");
    }

    @RequestMapping(value = {"/adicionar", "/alterar", "/remover"}, method = RequestMethod.POST, params = "action=cancelar")
	public String cancelar() {
		return "redirect:/elenco/lista";
	}
}