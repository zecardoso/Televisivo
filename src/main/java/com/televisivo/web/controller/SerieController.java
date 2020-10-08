package com.televisivo.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.televisivo.config.TelevisivoConfig;
import com.televisivo.model.Categoria;
import com.televisivo.model.Serie;
import com.televisivo.model.Servico;
import com.televisivo.repository.filters.SerieFilter;
import com.televisivo.repository.pagination.Pagina;
import com.televisivo.service.CategoriaService;
import com.televisivo.service.JasperReportsService;
import com.televisivo.service.SerieService;
import com.televisivo.service.ServicoService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/serie")
public class SerieController {

    private static final String SERIE = "serie";
    private static final String TEMPORADAS = "temporadas";
    private static final String SUCCESS = "success";
    private static final String LISTA = "redirect:/serie/lista";
    private static final String HTML_SERIE = "/serie/serie";
    private static final String HTML_SERIE_TEMPORADA = "/serie/serie/temporada";

    @Autowired
    private SerieService serieService;
    
    @Autowired
    private ServicoService servicoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private JasperReportsService jasperReportsService;
    
    @GetMapping("/lista")
    public ModelAndView lista(SerieFilter serieFilter, HttpServletRequest httpServletRequest, @RequestParam(value = "page", required = false) Optional<Integer> page, @RequestParam(value = "size", required = false) Optional<Integer> size) {
        Pageable pageable = PageRequest.of(page.orElse(TelevisivoConfig.INITIAL_PAGE), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        Pagina<Serie> paginaPagina = new Pagina<>(serieService.listaComPaginacao(serieFilter, pageable), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE), httpServletRequest);
        ModelAndView modelAndView = new ModelAndView("/serie/lista");
        modelAndView.addObject("pageSizes", TelevisivoConfig.PAGE_SIZES);
        modelAndView.addObject("tamanho", size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        modelAndView.addObject("pagina", paginaPagina);
        return modelAndView;
    }

    @GetMapping()
    public ModelAndView viewSalvar(Serie serie) {
        ModelAndView modelAndView = new ModelAndView(HTML_SERIE);
        modelAndView.addObject(SERIE, serie);
        return modelAndView;
    }

    @GetMapping("/{id}/temporadas")
    public ModelAndView viewSalvarTemporada(Serie serie) {
        ModelAndView modelAndView = new ModelAndView(HTML_SERIE_TEMPORADA);
        modelAndView.addObject(SERIE, serie);
        modelAndView.addObject(TEMPORADAS, serieService.temporadas(serie.getId()));
        return modelAndView;
    }

    @PostMapping()
    public String salvar(@Valid Serie serie, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "redirect:/serie";
        }
        serieService.save(serie);
        serieService.salvarTemporada(serie);
        attributes.addFlashAttribute(SUCCESS, "Registro adicionado com sucesso.");
		return "redirect:/serie/" + serie.getId() + "/alterar";
    }

    @GetMapping("{id}")
    public ModelAndView detalhar(@PathVariable("id") Long id) {
        Serie serie = serieService.getOne(id);
        ModelAndView modelAndView = new ModelAndView("/serie/detalhes");
        modelAndView.addObject(SERIE, serie);
        modelAndView.addObject(TEMPORADAS, serieService.temporadas(id));
        return modelAndView;
    }

    @GetMapping("/{id}/alterar")
    public ModelAndView viewAlterar(@PathVariable("id") Long id) {
        Serie serie = serieService.getOne(id);
        serie = serieService.adicionarTemporada(serie);
        ModelAndView modelAndView = new ModelAndView(HTML_SERIE);
        modelAndView.addObject(SERIE, serie);
        return modelAndView;
    }

    @PostMapping("/{id}/alterar")
    public String alterar(@PathVariable("id") Long id, @Valid Serie serie, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "redirect:/serie/" + serie.getId() + "/alterar";
        }
        serieService.update(serie);
        serieService.salvarTemporada(serie);
        serieService.atualizarQtdTemporadas(id);
        attributes.addFlashAttribute(SUCCESS, "Registro alterado com sucesso.");
        return "redirect:/serie/" + serie.getId();
    }

    // @PostMapping(value = "/{id}/alterar", params = "addRow")
    // public ModelAndView adicionarTemporada(@PathVariable("id") Long id, Serie serie) {
    //     serie = serieService.adicionarTemporada(serie);
    //     ModelAndView modelAndView = new ModelAndView(HTML_SERIE);
    //     modelAndView.addObject(SERIE, serie);
    //     return modelAndView;
    // }

    // @PostMapping(value = "/{id}/alterar", params = "removeRow")
    // public ModelAndView removerTemporada(@PathVariable("id") Long id, Serie serie, HttpServletRequest request) {
    //     int index = Integer.parseInt(request.getParameter("removeRow"));
    //     serie = serieService.removerTemporada(serie, index);
    //     ModelAndView modelAndView = new ModelAndView(HTML_SERIE);
    //     serieService.atualizarQtdTemporadas(id);
    //     modelAndView.addObject(SERIE, serie);
    //     return modelAndView;
    // }

    @GetMapping("/{id}/remover")
    public ModelAndView viewRemover(@PathVariable("id") Long id) {
        Serie serie = serieService.getOne(id);
        ModelAndView modelAndView = new ModelAndView("/serie/remover");
        modelAndView.addObject(SERIE, serie);
        modelAndView.addObject(TEMPORADAS, serieService.temporadas(id));
        return modelAndView;
    }

    @PostMapping("/{id}/remover")
    public String remover(@PathVariable("id") Long id, RedirectAttributes attributes) {
        serieService.deleteById(id);
        attributes.addFlashAttribute(SUCCESS, "Registro removido com sucesso.");
        return LISTA;
    }

    @ModelAttribute("servicos")
	public List<Servico> getServicos() {
		return servicoService.findAll();
    }
    
    @ModelAttribute("categorias")
	public List<Categoria> getCategorias() {
		return categoriaService.findAll();
    }
    
    @PostMapping(value = { "/{id}/alterar", "/{id}/remover" }, params = "cancelar")
	public String cancelar(@PathVariable("id") Long id) {
		return "redirect:/serie/{id}";
    }

    @PostMapping(value = { "", "/" }, params = "cancelar")
	public String cancelar() {
		return LISTA;
    }
    
    @GetMapping("/download")
    public void imprimeRelatorioDownload(HttpServletResponse response) {
    	JasperPrint jasperPrint = null;
    	jasperPrint = jasperReportsService.imprimeRelatorioDownload(SERIE);
    	response.setContentType("application/x-download");
    	response.setHeader("Content-Disposition", String.format("attachment; filename=\"series.pdf\""));
    	try {
			OutputStream out = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, out);
		} catch (IOException | JRException e) {
			e.printStackTrace();
		}
    }
    
    @GetMapping("/series.pdf")
    public ResponseEntity<byte[]> imprimeRelatorioPdf() {
    	byte[] relatorio = jasperReportsService.imprimeRelatorioNoNavegador(SERIE);
    	return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(relatorio);
    }
}