package com.televisivo.web.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.televisivo.config.TelevisivoConfig;
import com.televisivo.model.Elenco;
import com.televisivo.repository.filters.ElencoFilter;
import com.televisivo.repository.pagination.Pagina;
import com.televisivo.service.ElencoService;

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
@RequestMapping(value = "/elenco")
public class ElencoController {

    private static final String ELENCO = "elenco";
    private static final String SUCCESS = "success";
    private static final String LISTA = "redirect:/elenco/lista";

    @Autowired
    private ElencoService elencoService;

    @GetMapping("/lista")
    public ModelAndView lista(ElencoFilter elencoFilter, HttpServletRequest httpServletRequest, @RequestParam(value = "page", required = false) Optional<Integer> page, @RequestParam(value = "size", required = false) Optional<Integer> size) {
        Pageable pageable = PageRequest.of(page.orElse(TelevisivoConfig.INITIAL_PAGE), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        Pagina<Elenco> pagina = new Pagina<>(elencoService.listaComPaginacao(elencoFilter, pageable), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE), httpServletRequest);
        ModelAndView modelAndView = new ModelAndView("/elenco/lista");
        modelAndView.addObject("pageSizes", TelevisivoConfig.PAGE_SIZES);
        modelAndView.addObject("size", size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        modelAndView.addObject("pagina", pagina);
        return modelAndView;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastro(Elenco elenco) {
        ModelAndView modelAndView = new ModelAndView("/elenco/elenco");
        modelAndView.addObject(ELENCO, elenco);
        return modelAndView;
    }

    @GetMapping("/detalhes/{id}")
    public ModelAndView detalhes(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/elenco/detalhes");
        Elenco elenco = elencoService.getOne(id);
        modelAndView.addObject(ELENCO, elenco);
        return modelAndView;
    }

    @GetMapping("/alterar/{id}")
    public ModelAndView buscar(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/elenco/elenco");
        Elenco elenco = elencoService.getOne(id);
        modelAndView.addObject(ELENCO, elenco);
        return modelAndView;
    }

    @GetMapping("/remover/{id}")
    public ModelAndView removerId(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/elenco/remover");
        Elenco elenco = elencoService.getOne(id);
        modelAndView.addObject(ELENCO, elenco);
        return modelAndView;
    }

    @PostMapping(value = "/adicionar")
    public ModelAndView adicionar(@Valid Elenco elenco, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return cadastro(elenco);
        }
        elencoService.save(elenco);
        attributes.addFlashAttribute(SUCCESS, "Registro adicionado com sucesso.");
        return new ModelAndView(LISTA);
    }

    @PostMapping(value = "/alterar")
    public ModelAndView alterar(@Valid Elenco elenco, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return cadastro(elenco);
        }
        elencoService.update(elenco);
        attributes.addFlashAttribute(SUCCESS, "Registro alterado com sucesso.");
        return new ModelAndView(LISTA);
    }

    @PostMapping(value = "/remover")
    public ModelAndView remover(Elenco elenco, BindingResult result, RedirectAttributes attributes) {
        elencoService.deleteById(elenco.getId());
        attributes.addFlashAttribute(SUCCESS, "Registro removido com sucesso.");
        return new ModelAndView(LISTA);
    }

    @PostMapping(value = { "/adicionar", "/alterar", "/remover" }, params = "action=cancelar")
	public String cancelar() {
		return LISTA;
	}
}