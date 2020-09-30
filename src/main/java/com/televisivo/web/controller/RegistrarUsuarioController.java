package com.televisivo.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.televisivo.config.TelevisivoConfig;
import com.televisivo.model.Usuario;
import com.televisivo.model.VerificarToken;
import com.televisivo.service.RegistrarUsuarioService;
import com.televisivo.web.event.RegistrarUsuarioEvent;
import com.televisivo.web.event.UsuarioEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "registrar")
public class RegistrarUsuarioController {
    
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private RegistrarUsuarioService registrarUsuarioService;

    @GetMapping("/cadastrar")
    public ModelAndView registrarUsuario(Usuario usuario) {
        ModelAndView mv = new ModelAndView("/registrar/cadastro");
        mv.addObject("usuario", usuario);
        return mv;
    }

    @RequestMapping("/nao-encontrado")
	public ModelAndView registroUsuarioNaoEncontrado() {
		return new ModelAndView("/registrar/nao-encontrado");
	}

    @PostMapping("/salvar")
    public String salvar(@Valid Usuario usuario, BindingResult result, ModelMap model, HttpServletRequest request) {
        if (result.hasErrors()) {
            registrarUsuario(usuario);
        }
        registrarUsuarioService.registrarUsuario(usuario);
        enviarEmail(usuario, request);
        model.addAttribute("sucess", "Salvo, verifique seu email");
        return "registrar/cadastro";
    }

    @GetMapping("/confirmar-registro-usuario")
    public String confirmarRegistroUsuario(ModelMap model,@RequestParam("token") String token) {
        int result = registrarUsuarioService.verificarValidacaoToken(token);
        if (result == TelevisivoConfig.TOKEN_VALID) {
            Usuario usuario = registrarUsuarioService.getUsuario(token);
            if (usuario != null) {
                model.addAttribute("sucess", "conta registrada com sucesso");
                return "registrar/registro-confirmado";
            }
            if (result == TelevisivoConfig.TOKEN_INVALID) {
                model.addAttribute("fail", "falha no resgistro do usuario");
                model.addAttribute("fail", "chave de acesso inválida ou não exite");
            } else if (result == TelevisivoConfig.TOKEN_EXPIRED) {
                model.addAttribute("fail", "falha no resgistro do usuario");
                model.addAttribute("fail", "chave está expirada");
            } else {
                model.addAttribute("fail", "Usuario não usuario");
                return "registrar/cadastro";
            }
        }
        return "registrar/nao-encontrado";
    }

    @GetMapping("/reenviarToken")
    public void reenviarRegistrationToken(HttpServletRequest request, HttpServletResponse response, @RequestParam("token") String existingToken, RedirectAttributes attr) {
    	VerificarToken token = registrarUsuarioService.renovarValidacaoToken(existingToken);
        Usuario usuario = registrarUsuarioService.getUsuario(token.getToken());
		enviarEmail(usuario, request);
        String mensagem = "Enviamos mensagem para seu e-mail com link para realizar um novo registro ";
        try {
			request.getRequestDispatcher(String.format("/login?mensagem=%s", mensagem)).forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
    }

    private void enviarEmail(Usuario usuario, HttpServletRequest request) {
        UsuarioEvent usuarioEvent = new UsuarioEvent(TelevisivoConfig.getAppUrl(request), request.getLocale(), usuario);
        eventPublisher.publishEvent(new RegistrarUsuarioEvent(usuarioEvent));
    }
}