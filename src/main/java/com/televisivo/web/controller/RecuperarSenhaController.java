package com.televisivo.web.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.televisivo.config.TelevisivoConfig;
import com.televisivo.model.Usuario;
import com.televisivo.service.RecuperarSenhaService;
import com.televisivo.service.UsuarioService;
import com.televisivo.util.dto.AlterarSenha;
import com.televisivo.util.dto.Email;
import com.televisivo.web.event.RecuperarSenhaUsuarioEvent;
import com.televisivo.web.event.UsuarioEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value="/recuperar")
public class RecuperarSenhaController {
	
	@Autowired
    private ApplicationEventPublisher eventPublisher;
	
    @Autowired
	private RecuperarSenhaService recuperarSenhaService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/senha")
	public ModelAndView pegarEmail(Email email) {
		ModelAndView mv = new ModelAndView("/registrar/email");
		mv.addObject("email", email);
		return mv;
	}
	
    @PostMapping("/reset-senha")
    public  ModelAndView resetPassword( @Valid Email email, HttpServletRequest request, ModelMap model) {
    	Optional<Usuario> usuario = usuarioService.findUsuarioByEmail(email.getEmail());
        if ( usuario.isPresent() ) {
              emitirEmail(usuario.get(), request);
              model.addAttribute("success", "Enviamos link no seu e-mail para fazer a troca da senha");
        } else {
          	model.addAttribute("fail", "E-mail fornecido não foi encontrado.");
        }
        return new ModelAndView("/registrar/email");
    }
    
    @GetMapping("/trocar-senha")
    public String showChangePasswordPage(@RequestParam("token") String token, @RequestParam("id") String id, Model model, AlterarSenha password) {
        long idUser = Long.parseLong(id);
        int result = recuperarSenhaService.validarSenhaAlteradaComToken(idUser, token);
        if (result == TelevisivoConfig.TOKEN_VALID ) {
            model.addAttribute("password", password);
            return "/registrar/trocar-senha";
        }
        return "/login";
    }
    
    @PostMapping("/salvar-senha")
    public String savePassword(@Valid AlterarSenha password, BindingResult result, Model model) {
        if ( result.hasErrors()) {
    		model.addAttribute("fail","Erro no processamento para salvar a senha");
    		return "/registrar/trocar-senha";
   	    } else {
	        Usuario usuario= (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        recuperarSenhaService.alterarUsuarioSenha(usuario, password.getNewPassword());
	        model.addAttribute("success","Senha salva com sucesso");
   	    }
        return "/login";
    }
    
    private void emitirEmail(Usuario usuario, HttpServletRequest request) {
    	UsuarioEvent usuarioEvent = new UsuarioEvent(getAppUrl(request), request.getLocale(), usuario);
		eventPublisher.publishEvent(new RecuperarSenhaUsuarioEvent( usuarioEvent ));
	}
    
    private String getAppUrl(HttpServletRequest request) {
        return TelevisivoConfig.getAppUrl(request);
    }
}