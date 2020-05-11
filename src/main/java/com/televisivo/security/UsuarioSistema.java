package com.televisivo.security;

import com.televisivo.model.Usuario;

import org.springframework.security.core.userdetails.User;

import lombok.Getter;

@Getter
public class UsuarioSistema extends User {

    private Usuario usuario;

    public UsuarioSistema(Usuario usuario) {
        super(usuario.getUsername(), usuario.getPassword(), usuario.isEnabled(), usuario.isAccountNonExpired(), usuario.isCredentialsNonExpired(), usuario.isAccountNonLocked(), usuario.getAuthorities());
        this.usuario = usuario;
    }
}