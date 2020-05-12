package com.televisivo.web.exception;

import com.televisivo.security.UsuarioSistema;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class ResourcesExceptionHandler {
    
    @ModelAttribute("usuario_logado")
    public UsuarioSistema getUsuarioLogado(@AuthenticationPrincipal UsuarioSistema usuario_logado) {
        return (usuario_logado == null) ? null : usuario_logado;
    }
}