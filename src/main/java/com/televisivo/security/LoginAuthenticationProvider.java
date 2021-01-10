package com.televisivo.security;

import java.util.Optional;

import com.televisivo.model.Usuario;
import com.televisivo.service.UsuarioService;
import com.televisivo.service.exceptions.EmailUsuarioDeveSerInformadoException;
import com.televisivo.service.exceptions.SenhaUsuarioDeveSerInformadaException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
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
        if ("".equals(email)) {
            throw new EmailUsuarioDeveSerInformadoException("O email deve ser informado!");
        }
        if ("".equals(password)) {
            throw new SenhaUsuarioDeveSerInformadaException("A senha deve ser informada!");
        }
        Optional<Usuario> usuario = usuarioService.loginUsuarioByEmail(email);
        if (!usuario.isPresent()) {
            throw new UsernameNotFoundException("E-mail não está cadastrado!");
        }
        if (Boolean.FALSE.equals(email.equals(usuario.get().getEmail()) && usuario.get().isAtivo())) {
            throw new LockedException("Sua conta foi bolequeada!");
        }
        if (email.equals(usuario.get().getEmail()) && BCrypt.checkpw(password, usuario.get().getPassword())) {
            token = new UsernamePasswordAuthenticationToken(new UsuarioSistema(usuario.get()), usuario.get().getPassword(), usuario.get().getAuthorities());
        } else {
            throw new BadCredentialsException("Senha inválida!");
        }
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}