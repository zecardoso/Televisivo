package com.televisivo.web.event;

import java.io.Serializable;
import java.util.Locale;

import com.televisivo.model.Usuario;

import lombok.Data;

@Data
public class UsuarioEvent implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String url;
    private Locale locale;
    private Usuario usuario;

    public UsuarioEvent(String url, Locale locale, Usuario usuario) {
        this.url = url;
        this.locale = locale;
        this.usuario = usuario;
    }
}
