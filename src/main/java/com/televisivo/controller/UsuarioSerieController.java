package com.televisivo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.televisivo.model.Categoria;
import com.televisivo.model.Episodio;
import com.televisivo.model.Serie;
import com.televisivo.model.Temporada;
import com.televisivo.security.UsuarioSistema;
import com.televisivo.service.EpisodioService;
import com.televisivo.service.SerieService;
import com.televisivo.service.TemporadaService;
import com.televisivo.service.UsuarioEpisodioService;
import com.televisivo.service.UsuarioSerieService;
import com.televisivo.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuarioSerieController {

    private static final String LISTA = "lista";
    private static final String SERIE = "serie";
    private static final String SUCCESS = "success";
    private static final String USUARIO = "usuarioLogado";
    private static final String REMOVIDA = "Série removida com sucesso.";
    private static final String REMOVER = "remover";
    private static final String REDIRECT = "redirect:/";
    private static final String DETALHES = "redirect:./detalhes";
    private static final String SALVAS = "redirect:/series/salvas";
    private static final String ARQUIVADAS = "redirect:/series/arquivadas";
    private static final String DESMARCADO = "Episódio desmarcado com sucesso.";
    private static final String DESARQUIVADA = "Série desarquivada com sucesso.";
    private static final String ARQUIVADA = "Série arquivada com sucesso.";

    @Autowired
    private UsuarioSerieService usuarioSerieService;

    @Autowired
    private UsuarioEpisodioService usuarioEpisodioService;

    @Autowired
    private SerieService serieService;

    @Autowired
    private TemporadaService temporadaService;

    @Autowired
    private EpisodioService episodioService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/series/{id}/detalhes")
    public ModelAndView detalharSerie(@PathVariable("id") Long id) {
        Serie serie = serieService.getOne(id);
        ModelAndView modelAndView = new ModelAndView("/usuario_serie/detalhes_serie");
        modelAndView.addObject(SERIE, serie);
        modelAndView.addObject("temporadas", serieService.temporadas(serie));
        return modelAndView;
    }

    @GetMapping("/series/{idSerie}/temporada/{id}/detalhes")
    public ModelAndView detalharTemporada(@AuthenticationPrincipal UsuarioSistema usuarioLogado, @PathVariable("id") Long id) {
        Temporada temporada = temporadaService.getOne(id);
        ModelAndView modelAndView = new ModelAndView("/usuario_serie/detalhes_temporada");
        modelAndView.addObject("temporada", temporada);
        modelAndView.addObject("episodios", usuarioSerieService.episodios(usuarioLogado, id));
        return modelAndView;
    }

    @GetMapping("/series/{idSerie}/temporada/{idTemporada}/episodio/{id}/detalhes")
    public ModelAndView detalhesEpisodio(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/usuario_serie/detalhes_episodio");
        Episodio episodio = episodioService.getOne(id);
        modelAndView.addObject("episodio", episodio);
        return modelAndView;
    }

    @GetMapping("/series/salvas")
    public ModelAndView listaseries(@AuthenticationPrincipal UsuarioSistema usuarioLogado, Model model) {
        ModelAndView modelAndView = new ModelAndView("/usuario_serie/series");
        List<Serie> lista = usuarioSerieService.findAllSeries(usuarioLogado, false);
        modelAndView.addObject(LISTA, lista);
        modelAndView.addObject(USUARIO, usuarioService.getOne(usuarioLogado.getUsuario().getId()));
        model.addAttribute("title", "salvas");
        return modelAndView;
    }


    @GetMapping("/categoria/{id}")
    public ModelAndView listaseriesCat(@PathVariable("id") Categoria categoria, @AuthenticationPrincipal UsuarioSistema usuarioLogado, Model model) {
        ModelAndView modelAndView = new ModelAndView("/usuario_serie/serie_categoria");
        List<Serie> lista = usuarioSerieService.findAllSeriesCategoria(categoria.getId());
        modelAndView.addObject(LISTA, lista);
        modelAndView.addObject(USUARIO, usuarioService.getOne(usuarioLogado.getUsuario().getId()));
        model.addAttribute("title", categoria.getNome());
        return modelAndView;
    }


    @GetMapping("/series/arquivadas")
    public ModelAndView listaseriesarq(@AuthenticationPrincipal UsuarioSistema usuarioLogado, Model model) {
        ModelAndView modelAndView = new ModelAndView("/usuario_serie/series");
        List<Serie> lista = usuarioSerieService.findAllSeries(usuarioLogado, true);
        modelAndView.addObject(LISTA, lista);
        modelAndView.addObject(USUARIO, usuarioService.getOne(usuarioLogado.getUsuario().getId()));
        model.addAttribute("title", "arquivadas");
        return modelAndView;
    }

    @GetMapping("/episodios")
    public ModelAndView listaepisodios(@AuthenticationPrincipal UsuarioSistema usuarioLogado) {
        ModelAndView modelAndView = new ModelAndView("/usuario_episodio/lista");
        List<Episodio> lista = usuarioEpisodioService.findAllEpisodios(usuarioLogado);
        modelAndView.addObject(LISTA, lista);
        modelAndView.addObject(USUARIO, usuarioService.getOne(usuarioLogado.getUsuario().getId()));
        return modelAndView;
    }

    @PostMapping(value = "/acao", params = "salvar")
    public String salvar(@AuthenticationPrincipal UsuarioSistema usuarioLogado, HttpServletRequest request, RedirectAttributes attributes) {
        try {
            usuarioSerieService.salvar(usuarioLogado, Long.parseLong(request.getParameter("salvar")));
            attributes.addFlashAttribute(SUCCESS, "Série salva com sucesso.");
            return REDIRECT;
        } catch (Exception e) {
            attributes.addFlashAttribute(SUCCESS, "Série salva com sucesso.");
            return REDIRECT;
        }
    }

    @PostMapping(value = "/acao", params = REMOVER)
    public String removerHome(@AuthenticationPrincipal UsuarioSistema usuarioLogado, HttpServletRequest request, RedirectAttributes attributes) {
        try {
            usuarioSerieService.remover(usuarioLogado, Long.parseLong(request.getParameter(REMOVER)));
            attributes.addFlashAttribute(SUCCESS, REMOVIDA);
            return REDIRECT;
        } catch (Exception e) {
            attributes.addFlashAttribute(SUCCESS, REMOVIDA);
            return REDIRECT;
        }
    }

    @PostMapping(value = "/acao", params = "desarquivar")
    public String desarquivarHome(@AuthenticationPrincipal UsuarioSistema usuarioLogado, HttpServletRequest request, RedirectAttributes attributes) {
        try {
            usuarioSerieService.arquivada(usuarioLogado, Long.parseLong(request.getParameter("desarquivar")), false);
            attributes.addFlashAttribute(SUCCESS, DESARQUIVADA);
            return REDIRECT;
        } catch (Exception e) {
            attributes.addFlashAttribute(SUCCESS, DESARQUIVADA);
            return REDIRECT;
        }
    }

    @PostMapping(value = "/acao", params = "arquivar")
    public String arquivarHome(@AuthenticationPrincipal UsuarioSistema usuarioLogado, HttpServletRequest request, RedirectAttributes attributes) {
        try {
            usuarioSerieService.arquivada(usuarioLogado, Long.parseLong(request.getParameter("arquivar")), true);
            attributes.addFlashAttribute(SUCCESS, ARQUIVADA);
            return REDIRECT;
        } catch (Exception e) {
            attributes.addFlashAttribute(SUCCESS, ARQUIVADA);
            return REDIRECT;
        }
    }

    @PostMapping(value = "/series/salva", params = "arquivar")
    public String arquivar(@AuthenticationPrincipal UsuarioSistema usuarioLogado, HttpServletRequest request, RedirectAttributes attributes) {
        try {
            usuarioSerieService.arquivada(usuarioLogado, Long.parseLong(request.getParameter("arquivar")), true);
            attributes.addFlashAttribute(SUCCESS, ARQUIVADA);
            return SALVAS;
        } catch (Exception e) {
            attributes.addFlashAttribute(SUCCESS, ARQUIVADA);
            return SALVAS;
        }
    }

    @PostMapping(value = "/series/salva", params = REMOVER)
    public String removerSal(@AuthenticationPrincipal UsuarioSistema usuarioLogado, HttpServletRequest request, RedirectAttributes attributes) {
        try {
            usuarioSerieService.remover(usuarioLogado, Long.parseLong(request.getParameter(REMOVER)));
            attributes.addFlashAttribute(SUCCESS, REMOVIDA);
            return SALVAS;
        } catch (Exception e) {
            attributes.addFlashAttribute(SUCCESS, REMOVIDA);
            return SALVAS;
        }
    }

    @PostMapping(value = "/series/arquivada", params = "desarquivar")
    public String desarquivar(@AuthenticationPrincipal UsuarioSistema usuarioLogado, HttpServletRequest request, RedirectAttributes attributes) {
        try {
            usuarioSerieService.arquivada(usuarioLogado, Long.parseLong(request.getParameter("desarquivar")), false);
            attributes.addFlashAttribute(SUCCESS, DESARQUIVADA);
            return ARQUIVADAS;
        } catch (Exception e) {
            attributes.addFlashAttribute(SUCCESS, DESARQUIVADA);
            return ARQUIVADAS;
        }
    }

    @PostMapping(value = "/series/arquivada", params = REMOVER)
    public String removerArq(@AuthenticationPrincipal UsuarioSistema usuarioLogado, HttpServletRequest request, RedirectAttributes attributes) {
        try {
            usuarioSerieService.remover(usuarioLogado, Long.parseLong(request.getParameter(REMOVER)));
            attributes.addFlashAttribute(SUCCESS, REMOVIDA);
            return ARQUIVADAS;
        } catch (Exception e) {
            attributes.addFlashAttribute(SUCCESS, REMOVIDA);
            return ARQUIVADAS;
        }
    }

    @PostMapping(value = "/series/{id}/temporada/{id}/detalhes", params = "marcar")
    public String marcar(@AuthenticationPrincipal UsuarioSistema usuarioLogado, HttpServletRequest request, RedirectAttributes attributes) {
        try {
            usuarioEpisodioService.marcar(usuarioLogado, Long.parseLong(request.getParameter("marcar")));
            attributes.addFlashAttribute(SUCCESS, "Episódio marcado com sucesso.");
            return DETALHES;
        } catch (Exception e) {
            attributes.addFlashAttribute(SUCCESS, "Episódio marcado com sucesso.");
            return DETALHES;
        }
    }

    @PostMapping(value = "/series/{id}/temporada/{id}/detalhes", params = "desmarcar")
    public String desmarcar(@AuthenticationPrincipal UsuarioSistema usuarioLogado, HttpServletRequest request, RedirectAttributes attributes) {
        try {
            usuarioEpisodioService.desmarcar(usuarioLogado, Long.parseLong(request.getParameter("desmarcar")));
            attributes.addFlashAttribute(SUCCESS, DESMARCADO);
            return DETALHES;
        } catch (Exception e) {
            attributes.addFlashAttribute(SUCCESS, DESMARCADO);
            return DETALHES;
        }
    }

    @PostMapping(value = "/episodios", params = "desmarcar")
    public String desmarcarHome(@AuthenticationPrincipal UsuarioSistema usuarioLogado, HttpServletRequest request, RedirectAttributes attributes) {
        try {
            usuarioEpisodioService.desmarcar(usuarioLogado, Long.parseLong(request.getParameter("desmarcar")));
            attributes.addFlashAttribute(SUCCESS, DESMARCADO);
            return "redirect:/episodios";
        } catch (Exception e) {
            attributes.addFlashAttribute(SUCCESS, DESMARCADO);
            return "redirect:/episodios";
        }
    }
}