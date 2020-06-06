package com.televisivo.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.televisivo.config.TelevisivoConfig;
import com.televisivo.controller.page.PaginaWrapper;
import com.televisivo.model.Categoria;
import com.televisivo.model.Serie;
import com.televisivo.model.Servico;
import com.televisivo.repository.filters.SerieFilter;
import com.televisivo.service.CategoriaService;
import com.televisivo.service.SerieService;
import com.televisivo.service.ServicoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/serie")
public class SerieController {

    @Autowired
    private SerieService serieService;

    @Autowired
    private ServicoService servicoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/lista")
    public ModelAndView lista(SerieFilter serieFilter, HttpServletRequest httpServletRequest, @RequestParam(value = "page", required = false) Optional<Integer> page, @RequestParam(value = "size", required = false) Optional<Integer> size) {
        Pageable pageable = PageRequest.of(page.orElse(TelevisivoConfig.INITIAL_PAGE), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        PaginaWrapper<Serie> paginaWrapper = new PaginaWrapper(serieService.listaComPaginacao(serieFilter, pageable), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE), httpServletRequest);
        ModelAndView modelAndView = new ModelAndView("/serie/lista");
        modelAndView.addObject("pageSizes", TelevisivoConfig.PAGE_SIZES);
        modelAndView.addObject("size", size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        modelAndView.addObject("pagina", paginaWrapper);
        return modelAndView;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastro(Serie serie) {
        ModelAndView modelAndView = new ModelAndView("/serie/serie");
        modelAndView.addObject("serie", serie);
        return modelAndView;
    }

    @GetMapping("/detalhes/{id}")
    public ModelAndView detalhes(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/serie/detalhes");
        Serie serie = serieService.getOne(id);
        modelAndView.addObject("serie", serie);
        return modelAndView;
    }

    @GetMapping("/alterar/{id}")
    public ModelAndView alterarId(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/serie/serie");
        Serie serie = serieService.buscarPorIdTemporada(id);
        modelAndView.addObject("serie", serie);
        return modelAndView;
    }

    @GetMapping("/remover/{id}")
    public ModelAndView removerId(@PathVariable("id") Long id) {
        Serie serie = serieService.getOne(id);
        ModelAndView modelAndView = new ModelAndView("/serie/remover");
        modelAndView.addObject("serie", serie);
        return modelAndView;
    }

    @RequestMapping(value = "/adicionar", method = RequestMethod.POST)
    public ModelAndView adicionar(@Valid Serie serie, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return cadastro(serie);
        }
        serieService.save(serie);
        ModelAndView modelAndView = new ModelAndView("/serie/serie");
		modelAndView.addObject("serie", serie);
        attributes.addFlashAttribute("success", "Registro adicionado com sucesso.");
		return modelAndView;
    }

    @RequestMapping(value = "/alterar", method = RequestMethod.POST)
    public ModelAndView alterar(@Valid Serie serie, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return cadastro(serie);
        }
        serieService.update(serie);
        serieService.salvarTemporada(serie);
        attributes.addFlashAttribute("success", "Registro alterado com sucesso.");
        return new ModelAndView("redirect:/serie/lista");
    }

    @RequestMapping(value = "/remover", method = RequestMethod.POST)
    public ModelAndView remover(Serie serie, BindingResult result, RedirectAttributes attributes) {
        serieService.deleteById(serie.getId());
        attributes.addFlashAttribute("success", "Registro removido com sucesso.");
        return new ModelAndView("redirect:/serie/lista");
    }

    @RequestMapping(value = "/alterar", method = RequestMethod.POST, params = "addrow")
    public ModelAndView adicionarTemporada(@Valid Serie serie) {
        ModelAndView modelAndView = new ModelAndView("/serie/serie");
        serie = serieService.adicionarTemporada(serie);
        modelAndView.addObject("serie", serie);
        return modelAndView;
    }

    @RequestMapping(value = "/alterar", method = RequestMethod.POST, params = "removerow")
    public ModelAndView removerTemporada(Serie serie, HttpServletRequest request) {
        int index = Integer.valueOf(request.getParameter("removerow"));
        serie = serieService.removerTemporada(serie, index);
        ModelAndView modelAndView = new ModelAndView("/serie/serie");
        modelAndView.addObject("serie", serie);
        return modelAndView;
    }

    @ModelAttribute("servicos")
	public List<Servico> getServicos() {
		return servicoService.findAll();
    }
    
    @ModelAttribute("categorias")
	public List<Categoria> getCategorias() {
		return categoriaService.findAll();
    }
    
    @RequestMapping(value = {"/adicionar", "/alterar", "/remover"}, method = RequestMethod.POST, params = "action=cancelar")
	public String cancelar() {
		return "redirect:/serie/lista";
    }
}