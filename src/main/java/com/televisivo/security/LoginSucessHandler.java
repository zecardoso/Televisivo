package com.televisivo.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.televisivo.model.Usuario;
import com.televisivo.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginSucessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UsuarioService usuarioService;

    private RedirectStrategy RedirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        request.getSession().setMaxInactiveInterval(60 * 60);
        UsuarioSistema usuario_logado = (UsuarioSistema) authentication.getPrincipal();
        updateLoginUsuario(usuario_logado.getUsuario());
        RedirectStrategy.sendRedirect(request, response, "/home");
    }

    private void updateLoginUsuario(Usuario usuario) {
        usuarioService.updateLoginUsuario(usuario);
    }
}