package com.televisivo.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginFailuireHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String mensagem = "";
        if (exception.getClass() == UsernameNotFoundException.class) {
            mensagem= "Usuario nao cadastrado!";
        } else if (exception.getClass() == BadCredentialsException.class) {
            mensagem = "Senha invalida";
        } else {
            mensagem = "Erro inesperado, tente novamente mais tarde";
        }
        request.getRequestDispatcher(String.format("/login?mensagem=%s", mensagem)).forward(request, response);
    }
    
}