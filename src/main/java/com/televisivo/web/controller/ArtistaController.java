package com.televisivo.web.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.televisivo.config.TelevisivoConfig;
import com.televisivo.model.Artista;
import com.televisivo.repository.filters.ArtistaFilter;
import com.televisivo.repository.pagination.Pagina;
import com.televisivo.service.ArtistaService;

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
@RequestMapping(value = "/artista")
public class ArtistaController {

    private static final String ARTISTA = "artista";
    private static final String SUCCESS = "success";
    private static final String LISTA = "redirect:/artista/lista";

    @Autowired
    private ArtistaService artistaService;

    @GetMapping("/lista")
    public ModelAndView lista(ArtistaFilter artistaFilter, HttpServletRequest httpServletRequest, @RequestParam(value = "page", required = false) Optional<Integer> page, @RequestParam(value = "size", required = false) Optional<Integer> size) {
        Pageable pageable = PageRequest.of(page.orElse(TelevisivoConfig.INITIAL_PAGE), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        Pagina<Artista> pagina = new Pagina<>(artistaService.listaComPaginacao(artistaFilter, pageable), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE), httpServletRequest);
        ModelAndView modelAndView = new ModelAndView("/artista/lista");
        modelAndView.addObject("pageSizes", TelevisivoConfig.PAGE_SIZES);
        modelAndView.addObject("size", size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        modelAndView.addObject("pagina", pagina);
        return modelAndView;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastro(Artista artista) {
        ModelAndView modelAndView = new ModelAndView("/artista/artista");
        modelAndView.addObject(ARTISTA, artista);
        return modelAndView;
    }

    @GetMapping("/detalhes/{id}")
    public ModelAndView detalhes(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/artista/detalhes");
        Artista artista = artistaService.getOne(id);
        modelAndView.addObject(ARTISTA, artista);
        return modelAndView;
    }

    @GetMapping("/alterar/{id}")
    public ModelAndView buscar(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/artista/artista");
        Artista artista = artistaService.getOne(id);
        modelAndView.addObject(ARTISTA, artista);
        return modelAndView;
    }

    @GetMapping("/remover/{id}")
    public ModelAndView removerId(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/artista/remover");
        Artista artista = artistaService.getOne(id);
        modelAndView.addObject(ARTISTA, artista);
        return modelAndView;
    }

    @PostMapping(value = "/adicionar")
    public ModelAndView adicionar(@Valid Artista artista, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return cadastro(artista);
        }
        artistaService.save(artista);
        attributes.addFlashAttribute(SUCCESS, "Registro adicionado com sucesso.");
        return new ModelAndView(LISTA);
    }

    @PostMapping(value = "/alterar")
    public ModelAndView alterar(@Valid Artista artista, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return cadastro(artista);
        }
        artistaService.update(artista);
        attributes.addFlashAttribute(SUCCESS, "Registro alterado com sucesso.");
        return new ModelAndView(LISTA);
    }

    @PostMapping(value = "/remover")
    public ModelAndView remover(Artista artista, BindingResult result, RedirectAttributes attributes) {
        artistaService.deleteById(artista.getId());
        attributes.addFlashAttribute(SUCCESS, "Registro removido com sucesso.");
        return new ModelAndView(LISTA);
    }

    @PostMapping(value = { "/adicionar", "/alterar", "/remover" }, params = "action=cancelar")
	public String cancelar() {
		return LISTA;
	}
}