package com.televisivo.security;

import java.util.Optional;

import com.televisivo.model.Usuario;
import com.televisivo.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<Usuario> usuario = usuarioService.findUsuarioByEmail(email);
        if (!usuario.isPresent()) {
            throw new UsernameNotFoundException("Usuário não encontrado. " + email);
        }
        return new UsuarioSistema(usuario.get());
    }
}