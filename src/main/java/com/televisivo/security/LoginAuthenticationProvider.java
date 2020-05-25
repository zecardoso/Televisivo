package com.televisivo.security;

import java.util.Optional;

import com.televisivo.model.Usuario;
import com.televisivo.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class LoginAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        Optional<Usuario> usuario = usuarioService.loginUsuarioByEmail(email);
        if (!usuario.isPresent()) {
            throw new UsernameNotFoundException("Usuario nao esta cadastrado!")
        }
        if (email.equals(usuario.get().getPassword()) && BCrypt.checkpw(password, usuario.get().getPassword())) {
            token = new UsernamePasswordAuthenticationToken(new UsuarioSistema(usuario.get()), usuario.get().getPassword(), usuario.get().getAuthorities());
        } else {
            throw new BadCredentialsException("A senha informada e invalida!");
        }
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}