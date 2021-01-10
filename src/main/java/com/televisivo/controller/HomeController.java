package com.televisivo.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.televisivo.config.TelevisivoConfig;
import com.televisivo.model.Categoria;
import com.televisivo.model.Serie;
import com.televisivo.model.Servico;
import com.televisivo.model.Usuario;
import com.televisivo.model.enumerate.Genero;
import com.televisivo.repository.filters.SerieFilter;
import com.televisivo.repository.pagination.Pagina;
import com.televisivo.security.UsuarioSistema;
import com.televisivo.service.ContaService;
import com.televisivo.service.UsuarioEpisodioService;
import com.televisivo.service.UsuarioSerieService;
import com.televisivo.service.UsuarioService;
import com.televisivo.service.exceptions.ConfirmeSenhaNaoInformadaException;
import com.televisivo.service.exceptions.EmailCadastradoException;
import com.televisivo.service.exceptions.SenhaError;
import com.televisivo.service.exceptions.UsernameCadastradoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private static final String USUARIO = "usuario";
    private static final String SIGNUP = "redirect:/sign-up";

    @Autowired
    private ContaService contaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioSerieService usuarioSerieService;

    @Autowired
    private UsuarioEpisodioService usuarioEpisodioService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/")
    public ModelAndView lista(@AuthenticationPrincipal UsuarioSistema usuarioLogado, SerieFilter serieFilter, HttpServletRequest httpServletRequest, @RequestParam(value = "page", required = false) Optional<Integer> page, @RequestParam(value = "size", required = false) Optional<Integer> size) {
        if (usuarioLogado == null) {
            return new ModelAndView("sign-in");
        }
        Pageable pageable = PageRequest.of(page.orElse(TelevisivoConfig.INITIAL_PAGE), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        Pagina<Serie> pagina = new Pagina<>(usuarioSerieService.listaComPaginacao(serieFilter, pageable, usuarioLogado), size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE), httpServletRequest);
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("pageSizes", TelevisivoConfig.PAGE_SIZES);
        modelAndView.addObject("size", size.orElse(TelevisivoConfig.INITIAL_PAGE_SIZE));
        modelAndView.addObject("pagina", pagina);
        modelAndView.addObject(USUARIO, usuarioService.getOne(usuarioLogado.getUsuario().getId()));
        atualiza(usuarioLogado);
        return modelAndView;
    }

    @RequestMapping(value = "/sign-in", method = { RequestMethod.GET, RequestMethod.POST })
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
        ModelAndView modelAndView = new ModelAndView("sign-in");
        modelAndView.addObject(USUARIO, usuario);
        return modelAndView;
    }

    @GetMapping("/sign-up")
    public ModelAndView viewSalvar(Usuario usuario) {
        ModelAndView modelAndView = new ModelAndView("sign-up");
        modelAndView.addObject(USUARIO, usuario);
        return modelAndView;
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes, Model model) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute(FAIL, "Verifique os campos!");
            return SIGNUP;
        }
        try {
            contaService.save(usuario);
        } catch (EmailCadastradoException e) {
            attributes.addFlashAttribute(FAIL, e.getMessage());
            result.rejectValue("email", e.getMessage());
            return SIGNUP;
        } catch (UsernameCadastradoException e) {
            attributes.addFlashAttribute(FAIL, e.getMessage());
            result.rejectValue("username", e.getMessage());
            return SIGNUP;
        } catch (SenhaError e) {
            attributes.addFlashAttribute(FAIL, e.getMessage());
            result.rejectValue("password", e.getMessage());
            return SIGNUP;
        } catch (ConfirmeSenhaNaoInformadaException e) {
            attributes.addFlashAttribute(FAIL, e.getMessage());
            result.rejectValue("contraSenha", e.getMessage());
            return SIGNUP;
        }

        attributes.addFlashAttribute(SUCCESS, "Registro adicionado.");
        model.addAttribute("email", usuario.getEmail());
        model.addAttribute("password", usuario.getPassword());
        return "redirect:/";
    }

    @ModelAttribute("generos")
    public Genero[] getGeneros() {
        return Genero.values();
    }

    @ModelAttribute("categorias")
	public List<Categoria> getCategorias() {
		return usuarioSerieService.findAllCategorias();
    }

    @ModelAttribute("servicos")
	public List<Servico> getServicos() {
		return usuarioSerieService.findAllServicos();
    }

    public void atualiza(@AuthenticationPrincipal UsuarioSistema usuarioLogado) {
        usuarioSerieService.atualizarQtdSeries(usuarioLogado);
        usuarioSerieService.atualizarQtdSeriesArq(usuarioLogado);
        usuarioEpisodioService.atualizarQtdEpisodios(usuarioLogado);
    }
}