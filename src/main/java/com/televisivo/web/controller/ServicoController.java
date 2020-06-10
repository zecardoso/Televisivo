package com.televisivo.web.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.televisivo.config.TelevisivoConfig;
import com.televisivo.model.Servico;
import com.televisivo.repository.filters.ServicoFilter;
import com.televisivo.repository.pagination.Pagina;
import com.televisivo.service.ServicoService;

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
@RequestMapping(value = "/servico")
public class ServicoController {

    private static final String SERVICO = "servico";
    private static final String SUCCESS = "success";
    private static final String LISTA = "redirect:/servico/lista";

    @Autowired
    private ServicoService servicoService;

    @GetMapping("/lista")
    public ModelAndView lista(ServicoFilter servicoFilter, HttpServletRequest httpServletRequest, @RequestParam(value = "page", required = false) Optional<Integer> page, @RequestParam(value = "size", required = false) Optional<Integer> size) {
        Pageable pageable = PageRequest.of(page.orElse(TelevisivoConfig.INITIAL_PAGE), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        Pagina<Servico> pagina = new Pagina<>(servicoService.listaComPaginacao(servicoFilter, pageable), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE), httpServletRequest);
        ModelAndView modelAndView = new ModelAndView("/servico/lista");
        modelAndView.addObject("pageSizes", TelevisivoConfig.PAGE_SIZES);
        modelAndView.addObject("size", size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        modelAndView.addObject("pagina", pagina);
        return modelAndView;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastro(Servico servico) {
        ModelAndView modelAndView = new ModelAndView("/servico/servico");
        modelAndView.addObject(SERVICO, servico);
        return modelAndView;
    }

    @GetMapping("/detalhes/{id}")
    public ModelAndView detalhes(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/servico/detalhes");
        Servico servico = servicoService.getOne(id);
        modelAndView.addObject(SERVICO, servico);
        return modelAndView;
    }

    @GetMapping("/alterar/{id}")
    public ModelAndView alterarId(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/servico/servico");
        Servico servico = servicoService.getOne(id);
        modelAndView.addObject(SERVICO, servico);
        return modelAndView;
    }

    @GetMapping("/remover/{id}")
    public ModelAndView removerId(@PathVariable("id") Long id) {
        Servico servico = servicoService.getOne(id);
        ModelAndView modelAndView = new ModelAndView("/servico/remover");
        modelAndView.addObject(SERVICO, servico);
        return modelAndView;
    }

    @PostMapping(value = "/adicionar")
    public ModelAndView adicionar(@Valid Servico servico, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return cadastro(servico);
        }
        servicoService.save(servico);
        attributes.addFlashAttribute(SUCCESS, "Registro adicionado com sucesso.");
        return new ModelAndView(LISTA);
    }

    @PostMapping(value = "/alterar")
    public ModelAndView update(@Valid Servico servico, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return cadastro(servico);
        }
        servicoService.update(servico);
        attributes.addFlashAttribute(SUCCESS, "Registro alterado com sucesso.");
        return new ModelAndView(LISTA);
    }

    @PostMapping(value = "/remover")
    public ModelAndView remover(Servico servico, BindingResult result, RedirectAttributes attributes) {
        servicoService.deleteById(servico.getId());
        attributes.addFlashAttribute(SUCCESS, "Registro removido com sucesso.");
        return new ModelAndView(LISTA);
    }

    @PostMapping(value = {"/adicionar", "/alterar", "/remover"}, params = "action=cancelar")
	public String cancelar() {
		return LISTA;
	}
}