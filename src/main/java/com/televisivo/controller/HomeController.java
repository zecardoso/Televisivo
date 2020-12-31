package com.televisivo.controller;

import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.televisivo.config.TelevisivoConfig;
import com.televisivo.model.Serie;
import com.televisivo.model.Usuario;
import com.televisivo.model.enumerate.Genero;
import com.televisivo.repository.filters.SerieFilter;
import com.televisivo.repository.pagination.Pagina;
import com.televisivo.security.UsuarioSistema;
import com.televisivo.service.SerieService;
import com.televisivo.service.UsuarioService;
import com.televisivo.service.exceptions.EmailCadastradoException;
import com.televisivo.service.exceptions.SenhaError;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    private static final String CADASTRO = "redirect:/login";
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private UsuarioService contaService;

    @Autowired
    private SerieService serieService;

    @Autowired
    private UsuarioService usuarioService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/")
    public ModelAndView lista(@AuthenticationPrincipal UsuarioSistema usuarioLogado, ModelMap model, SerieFilter serieFilter, HttpServletRequest httpServletRequest, @RequestParam(value = "page", required = false) Optional<Integer> page, @RequestParam(value = "size", required = false) Optional<Integer> size) {
        if (usuarioLogado == null) {
            return new ModelAndView("login");
        }
        Pageable pageable = PageRequest.of(page.orElse(TelevisivoConfig.INITIAL_PAGE), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        Pagina<Serie> paginaPagina = new Pagina<>(serieService.listaComPaginacao(serieFilter, pageable), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE), httpServletRequest);
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("pageSizes", TelevisivoConfig.PAGE_SIZES);
        modelAndView.addObject("tamanho", size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        modelAndView.addObject("pagina", paginaPagina);
        modelAndView.addObject("usuario", usuarioService.getOne(usuarioLogado.getUsuario().getId()));
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView loginPage(Usuario usuario, @RequestParam(value = "mensagem", required = false) String mensagem, Model model) {
        if (Objects.isNull(mensagem)) {
            model.addAttribute("acao", false);
        } else if (mensagem.trim().equals("true")) {
            model.addAttribute("acao", false);
            model.addAttribute("mensagem", "Sessão inválida!");
        } else {
            model.addAttribute("acao", true);
            model.addAttribute("mensagem", mensagem);
        }
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("usuario", usuario);
        return modelAndView;
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute(FAIL, "Verifique os campos!");
            return CADASTRO;
        }
        contaService.save(usuario);
        attributes.addFlashAttribute(SUCCESS, "Registro adicionado com sucesso.");
        return "redirect:/";
    }

    @ModelAttribute("generos")
    public Genero[] getGeneros() {
        return Genero.values();
    }
}