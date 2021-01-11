package com.televisivo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.televisivo.model.Categoria;
import com.televisivo.model.Episodio;
import com.televisivo.model.Serie;
import com.televisivo.model.Servico;
import com.televisivo.repository.filters.SerieFilter;
import com.televisivo.security.UsuarioSistema;
import com.televisivo.service.SerieService;
import com.televisivo.service.UsuarioEpisodioService;
import com.televisivo.service.UsuarioSerieService;
import com.televisivo.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuarioSerieController {

    private static final String LISTA = "lista";
    private static final String SERIE = "serie";
    private static final String SUCCESS = "success";
    private static final String USUARIO = "usuario";
    private static final String REMOVIDA = "Série removida.";
    private static final String SALVA = "Série salva.";
    private static final String REMOVER = "remover";
    private static final String REDIRECT = "redirect:";
    private static final String DESMARCADO = "Episódio desmarcado.";
    private static final String DESARQUIVADA = "Série desarquivada.";
    private static final String ARQUIVADA = "Série arquivada.";
    private static final String REFERER = "Referer";
    private static final String ARQUIVAR = "arquivar";
    private static final String DESARQUIVAR = "desarquivar";

    @Autowired
    private UsuarioSerieService usuarioSerieService;

    @Autowired
    private UsuarioEpisodioService usuarioEpisodioService;

    @Autowired
    private SerieService serieService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/serie/{id}")
    public ModelAndView detalharSerie(@AuthenticationPrincipal UsuarioSistema usuarioLogado, @PathVariable("id") Long id, SerieFilter serieFilter) {
        Serie serie = serieService.getOne(id);
        ModelAndView modelAndView = new ModelAndView("/usuario_serie/detalhes_serie");
        modelAndView.addObject(SERIE, usuarioSerieService.verifica(serie, usuarioLogado));
        modelAndView.addObject("temporadas", serieService.temporadas(serie));
        for (int i = 0; i < serieService.temporadas(serie).size(); i++) {
            modelAndView.addObject("episodios", usuarioSerieService.episodios(usuarioLogado, serieService.temporadas(serie).get(i).getId()));
        }
        modelAndView.addObject(USUARIO, usuarioService.getOne(usuarioLogado.getUsuario().getId()));
        atualiza(usuarioLogado);
        return modelAndView;
    }

    @GetMapping("/serie/salvas")
    public ModelAndView listaseries(@AuthenticationPrincipal UsuarioSistema usuarioLogado, Model model, SerieFilter serieFilter) {
        ModelAndView modelAndView = new ModelAndView("/usuario_serie/series");
        List<Serie> lista = usuarioSerieService.findAllSeries(usuarioLogado, false);
        modelAndView.addObject(LISTA, lista);
        modelAndView.addObject(USUARIO, usuarioService.getOne(usuarioLogado.getUsuario().getId()));
        model.addAttribute("title", "salvas");
        atualiza(usuarioLogado);
        return modelAndView;
    }

    @GetMapping("/categoria/{id}")
    public ModelAndView listaseriesCategoria(@PathVariable("id") Categoria categoria, @AuthenticationPrincipal UsuarioSistema usuarioLogado, Model model, SerieFilter serieFilter) {
        ModelAndView modelAndView = new ModelAndView("/usuario_serie/atributo");
        List<Serie> lista = usuarioSerieService.findAllSeriesBy(categoria.getSeries(), usuarioLogado);
        modelAndView.addObject(LISTA, lista);
        modelAndView.addObject(USUARIO, usuarioService.getOne(usuarioLogado.getUsuario().getId()));
        model.addAttribute("atributo", categoria);
        model.addAttribute("breadcrumb", "Categoria");
        atualiza(usuarioLogado);
        return modelAndView;
    }

    @GetMapping("/servico/{id}")
    public ModelAndView listaseriesServico(@PathVariable("id") Servico servico, @AuthenticationPrincipal UsuarioSistema usuarioLogado, Model model, SerieFilter serieFilter) {
        ModelAndView modelAndView = new ModelAndView("/usuario_serie/atributo");
        List<Serie> lista = usuarioSerieService.findAllSeriesBy(servico.getSeries(), usuarioLogado);
        modelAndView.addObject(LISTA, lista);
        modelAndView.addObject(USUARIO, usuarioService.getOne(usuarioLogado.getUsuario().getId()));
        model.addAttribute("atributo", servico);
        model.addAttribute("breadcrumb", "Serviço");
        atualiza(usuarioLogado);
        return modelAndView;
    }

    @GetMapping("/serie/arquivadas")
    public ModelAndView listaseriesarq(@AuthenticationPrincipal UsuarioSistema usuarioLogado, Model model, SerieFilter serieFilter) {
        ModelAndView modelAndView = new ModelAndView("/usuario_serie/series");
        List<Serie> lista = usuarioSerieService.findAllSeries(usuarioLogado, true);
        modelAndView.addObject(LISTA, lista);
        modelAndView.addObject(USUARIO, usuarioService.getOne(usuarioLogado.getUsuario().getId()));
        model.addAttribute("title", "arquivadas");
        atualiza(usuarioLogado);
        return modelAndView;
    }

    @GetMapping("/episodio/marcados")
    public ModelAndView listaepisodios(@AuthenticationPrincipal UsuarioSistema usuarioLogado, SerieFilter serieFilter) {
        ModelAndView modelAndView = new ModelAndView("/usuario_episodio/lista");
        List<Episodio> lista = usuarioEpisodioService.findAllEpisodios(usuarioLogado);
        modelAndView.addObject(LISTA, lista);
        modelAndView.addObject(USUARIO, usuarioService.getOne(usuarioLogado.getUsuario().getId()));
        atualiza(usuarioLogado);
        return modelAndView;
    }

    @PostMapping(value = "/acao", params = "salvar")
    public String salvar(@AuthenticationPrincipal UsuarioSistema usuarioLogado, HttpServletRequest request, RedirectAttributes attributes) {
        try {
            usuarioSerieService.salvar(usuarioLogado, Long.parseLong(request.getParameter("salvar")));
            attributes.addFlashAttribute(SUCCESS, SALVA);
            return REDIRECT + request.getHeader(REFERER);
        } catch (Exception e) {
            attributes.addFlashAttribute(SUCCESS, SALVA);
            return REDIRECT + request.getHeader(REFERER);
        }
    }

    @PostMapping(value = "/acao", params = REMOVER)
    public String remover(@AuthenticationPrincipal UsuarioSistema usuarioLogado, HttpServletRequest request, RedirectAttributes attributes) {
        try {
            usuarioSerieService.remover(usuarioLogado, Long.parseLong(request.getParameter(REMOVER)));
            attributes.addFlashAttribute(SUCCESS, REMOVIDA);
            return REDIRECT + request.getHeader(REFERER);
        } catch (Exception e) {
            attributes.addFlashAttribute(SUCCESS, REMOVIDA);
            return REDIRECT + request.getHeader(REFERER);
        }
    }

    @PostMapping(value = "/acao", params = DESARQUIVAR)
    public String desarquivar(@AuthenticationPrincipal UsuarioSistema usuarioLogado, HttpServletRequest request, RedirectAttributes attributes) {
        try {
            usuarioSerieService.arquivada(usuarioLogado, Long.parseLong(request.getParameter(DESARQUIVAR)), false);
            attributes.addFlashAttribute(SUCCESS, DESARQUIVADA);
            return REDIRECT + request.getHeader(REFERER);
        } catch (Exception e) {
            attributes.addFlashAttribute(SUCCESS, DESARQUIVADA);
            return REDIRECT + request.getHeader(REFERER);
        }
    }

    @PostMapping(value = "/acao", params = ARQUIVAR)
    public String arquivar(@AuthenticationPrincipal UsuarioSistema usuarioLogado, HttpServletRequest request, RedirectAttributes attributes) {
        try {
            usuarioSerieService.arquivada(usuarioLogado, Long.parseLong(request.getParameter(ARQUIVAR)), true);
            attributes.addFlashAttribute(SUCCESS, ARQUIVADA);
            return REDIRECT + request.getHeader(REFERER);
        } catch (Exception e) {
            attributes.addFlashAttribute(SUCCESS, ARQUIVADA);
            return REDIRECT + request.getHeader(REFERER);
        }
    }

    @PostMapping(value = "/acao", params = "marcar")
    public String marcar(@AuthenticationPrincipal UsuarioSistema usuarioLogado, HttpServletRequest request, RedirectAttributes attributes) {
        try {
            usuarioEpisodioService.marcar(usuarioLogado, Long.parseLong(request.getParameter("marcar")));
            attributes.addFlashAttribute(SUCCESS, "Episódio marcado.");
            return REDIRECT + request.getHeader(REFERER);
        } catch (Exception e) {
            attributes.addFlashAttribute(SUCCESS, "Episódio marcado.");
            return REDIRECT + request.getHeader(REFERER);
        }
    }

    @PostMapping(value = "/acao", params = "desmarcar")
    public String desmarcar(@AuthenticationPrincipal UsuarioSistema usuarioLogado, HttpServletRequest request, RedirectAttributes attributes) {
        try {
            usuarioEpisodioService.desmarcar(usuarioLogado, Long.parseLong(request.getParameter("desmarcar")));
            attributes.addFlashAttribute(SUCCESS, DESMARCADO);
            return REDIRECT + request.getHeader(REFERER);
        } catch (Exception e) {
            attributes.addFlashAttribute(SUCCESS, DESMARCADO);
            return REDIRECT + request.getHeader(REFERER);
        }
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