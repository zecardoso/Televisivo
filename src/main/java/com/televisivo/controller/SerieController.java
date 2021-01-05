package com.televisivo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.televisivo.config.TelevisivoConfig;
import com.televisivo.model.Categoria;
import com.televisivo.model.Serie;
import com.televisivo.model.Servico;
import com.televisivo.model.Temporada;
import com.televisivo.repository.filters.SerieFilter;
import com.televisivo.repository.pagination.Pagina;
import com.televisivo.service.CategoriaService;
import com.televisivo.service.JasperReportsService;
import com.televisivo.service.SerieService;
import com.televisivo.service.ServicoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

@Controller
@RequestMapping("/serie")
public class SerieController {

    private static final String SERIE = "serie";
    private static final String TEMPORADAS = "temporadas";
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private static final String MESSAGE = "Verifique os campos!";
    private static final String DETALHES = "redirect:./detalhes";
    private static final String HTML_SERIE = "/serie/serie";

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
        modelAndView.addObject("size", size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        modelAndView.addObject("pagina", paginaPagina);
        return modelAndView;
    }

    @GetMapping("/cadastro")
    public ModelAndView viewSalvar(Serie serie) {
        ModelAndView modelAndView = new ModelAndView(HTML_SERIE);
        modelAndView.addObject(SERIE, serie);
        return modelAndView;
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Serie serie, BindingResult result, RedirectAttributes attributes, @RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
        if (result.hasErrors()) {
            attributes.addFlashAttribute(FAIL, MESSAGE);
            return "redirect:/serie/cadastro";
        }
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        serie.setPhotos(fileName);
        Serie saveSerie = serieService.save(serie);

        String uploadDir = "serie-imagem/" + saveSerie.getId();

        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()){
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e){
            throw new IOException("Não foi possivel salvar a imagem :" + fileName);
        }
        serieService.salvarTemporada(serie);
        attributes.addFlashAttribute(SUCCESS, "Série adicionada com sucesso.");
        return "redirect:./" + serie.getId() + "/alterar";
    }

    @GetMapping("{id}/detalhes")
    public ModelAndView detalhar(@PathVariable("id") Long id) {
        Serie serie = serieService.getOne(id);
        ModelAndView modelAndView = new ModelAndView("/serie/detalhes");
        modelAndView.addObject(SERIE, serie);
        modelAndView.addObject(TEMPORADAS, serieService.temporadas(serie));
        return modelAndView;
    }

    @GetMapping("/{id}/alterar")
    public ModelAndView viewAlterar(@PathVariable("id") Long id) {
        Serie serie = serieService.getOne(id);
        ModelAndView modelAndView = new ModelAndView(HTML_SERIE);
        modelAndView.addObject(SERIE, serie);
        modelAndView.addObject(TEMPORADAS, serieService.temporadas(serie));
        return modelAndView;
    }

    @PostMapping("/{id}/alterar")
    public String alterar(@PathVariable("id") Long id, @Valid Serie serie, BindingResult result, RedirectAttributes attributes, @RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
        if (result.hasErrors()) {
            attributes.addFlashAttribute(FAIL, MESSAGE);
            return "redirect:./alterar";
        }
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        serie.setPhotos(fileName);

        serieService.salvarTemporada(serie);
        serieService.atualizarQtdTemporadas(serie);

        Serie saveSerie = serieService.update(serie);

        String uploadDir = "serie-imagem/" + saveSerie.getId();

        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()){
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e){
            throw new IOException("Não foi possivel salvar a imagem :" + fileName);
        }
        attributes.addFlashAttribute(SUCCESS, "Série alterada com sucesso.");
        return DETALHES;
    }

    @PostMapping(value = "/{id}/alterar", params = "addRow")
    public ModelAndView adicionarTemporada(@PathVariable("id") Long id, Serie serie, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute(FAIL, MESSAGE);
            return viewAlterar(id);
        }
        serieService.salvarTemporada(serie);
        ModelAndView modelAndView = new ModelAndView(HTML_SERIE);
        modelAndView.addObject(SERIE, serieService.adicionarTemporada(serie));
        modelAndView.addObject(TEMPORADAS, serieService.temporadas(serie));
        return modelAndView;
    }

    @PostMapping(value = "/{id}/alterar", params = "removeRow")
    public ModelAndView removerTemporada(@PathVariable("id") Long id, Serie serie, HttpServletRequest request) {
        Temporada temporada = serieService.findTemporadaByIdTemporada(Long.parseLong(request.getParameter("removeRow")));
        serieService.removerTemporada(temporada);
        serieService.atualizarQtdTemporadas(serie);
        ModelAndView modelAndView = new ModelAndView(HTML_SERIE);
        modelAndView.addObject(SERIE, temporada.getSerie());
        return modelAndView;
    }

    @GetMapping("/{id}/remover")
    public ModelAndView viewRemover(@PathVariable("id") Long id) {
        Serie serie = serieService.getOne(id);
        ModelAndView modelAndView = new ModelAndView("/serie/remover");
        modelAndView.addObject(SERIE, serie);
        modelAndView.addObject(TEMPORADAS, serieService.temporadas(serie));
        return modelAndView;
    }

    @PostMapping("/{id}/remover")
    public String remover(@PathVariable("id") Long id, RedirectAttributes attributes) {
        serieService.deleteById(id);
        attributes.addFlashAttribute(SUCCESS, "Série removida com sucesso.");
        return "redirect:../lista";
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
	public String cancelar() {
        return DETALHES;
    }

    @PostMapping(value = "/salvar", params = "cancelar")
	public String cancelarCadastro() {
		return "redirect:./lista";
    }

    @GetMapping("/download")
    public void imprimeRelatorioDownload(HttpServletResponse response) {
    	JasperPrint jasperPrint = null;
    	jasperPrint = jasperReportsService.imprimeRelatorioDownload(SERIE);
    	response.setContentType("application/x-download");
    	response.setHeader("Content-Disposition", String.format("attachment; filename=%s", "series.pdf"));
    	try {
			OutputStream out = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, out);
		} catch (IOException | JRException e) {
			e.printStackTrace();
		}
    }

    @GetMapping("/series")
    public ResponseEntity<byte[]> imprimeRelatorioPdf() {
    	byte[] relatorio = jasperReportsService.imprimeRelatorioNoNavegador(SERIE);
    	return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(relatorio);
    }
}