package com.televisivo.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.televisivo.config.TelevisivoConfig;
import com.televisivo.model.Role;
import com.televisivo.model.Usuario;
import com.televisivo.model.enumerate.Genero;
import com.televisivo.repository.filters.UsuarioFilter;
import com.televisivo.repository.pagination.Pagina;
import com.televisivo.service.JasperReportsService;
import com.televisivo.service.RoleService;
import com.televisivo.service.UsuarioService;
import com.televisivo.service.exceptions.EmailCadastradoException;
import com.televisivo.service.exceptions.SenhaError;

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

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private static final String USUARIO = "usuario";
    private static final String SUCCESS = "success";
    private static final String DETALHES = "redirect:./detalhes";
    private static final String CADASTRO = "redirect:./cadastro";
    private static final String ALTERAR = "redirect:./alterar";
    private static final String FAIL = "fail";

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private JasperReportsService jasperReportsService;

    @GetMapping("/lista")
    public ModelAndView lista(UsuarioFilter usuarioFilter, HttpServletRequest httpServletRequest, @RequestParam(value = "page", required = false) Optional<Integer> page, @RequestParam(value = "size", required = false) Optional<Integer> size) {
        Pageable pageable = PageRequest.of(page.orElse(TelevisivoConfig.INITIAL_PAGE), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        Pagina<Usuario> pagina = new Pagina<>(usuarioService.listaComPaginacao(usuarioFilter, pageable), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE), httpServletRequest);
        ModelAndView modelAndView = new ModelAndView("/usuario/lista");
        modelAndView.addObject("pageSizes", TelevisivoConfig.PAGE_SIZES);
        modelAndView.addObject("size", size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        modelAndView.addObject("pagina", pagina);
        return modelAndView;
    }

    @GetMapping("/cadastro")
    public ModelAndView viewSalvar(Usuario usuario) {
        ModelAndView modelAndView = new ModelAndView("/usuario/usuario");
        modelAndView.addObject(USUARIO, usuario);
        return modelAndView;
    }

    @GetMapping("/{id}/detalhes")
    public ModelAndView detalhes(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/usuario/detalhes");
        modelAndView.addObject(USUARIO, usuarioService.getOne(id));
        return modelAndView;
    }

    @GetMapping("/{id}/alterar")
    public ModelAndView viewAlterar(@PathVariable("id") Long id) {
        return viewSalvar(usuarioService.getOne(id));
    }

    @GetMapping("/{id}/remover")
    public ModelAndView viewRemover(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/usuario/remover");
        modelAndView.addObject(USUARIO, usuarioService.getOne(id));
        return modelAndView;
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute(FAIL, "Verifique os campos!");
            return CADASTRO;
        }
        try {
            usuarioService.save(usuario);
        } catch (EmailCadastradoException e) {
            result.rejectValue("email", e.getMessage());
            return CADASTRO;
        } catch (SenhaError e) {
            result.rejectValue("password", e.getMessage());
            return CADASTRO;
        }
        attributes.addFlashAttribute(SUCCESS, "Registro adicionado com sucesso.");
        return "redirect:./lista";
    }

    @PostMapping("/{id}/alterar")
    public String alterar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute(FAIL, "Verifique os campos!");
            return ALTERAR;
        }
        try {
			usuarioService.update(usuario);
		} catch(EmailCadastradoException e) {
			result.rejectValue("email", e.getMessage());
			return ALTERAR;
		} catch (SenhaError e) {
            result.rejectValue("password", e.getMessage());
            return ALTERAR;
        }
        attributes.addFlashAttribute(SUCCESS, "Registro alterado com sucesso.");
        return DETALHES;
    }

    @PostMapping("/{id}/remover")
    public String remover(@PathVariable("id") Long id, RedirectAttributes attributes) {
        usuarioService.deleteById(id);
        attributes.addFlashAttribute(SUCCESS, "Registro removido com sucesso.");
        return "redirect:../lista";
    }

    @ModelAttribute("roles")
    public List<Role> getRoles() {
        return roleService.findAll();
    }

    @ModelAttribute("generos")
    public Genero[] getGeneros() {
        return Genero.values();
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
    	jasperPrint = jasperReportsService.imprimeRelatorioDownload(USUARIO);
    	response.setContentType("application/x-download");
    	response.setHeader("Content-Disposition", String.format("attachment; filename=%s", "usuarios.pdf"));
    	try {
			OutputStream out = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, out);
		} catch (IOException | JRException e) {
			e.printStackTrace();
		}
    }

    @GetMapping("/usuarios")
    public ResponseEntity<byte[]> imprimeRelatorioPdf() {
    	byte[] relatorio = jasperReportsService.imprimeRelatorioNoNavegador(USUARIO);
    	return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(relatorio);
    }
}