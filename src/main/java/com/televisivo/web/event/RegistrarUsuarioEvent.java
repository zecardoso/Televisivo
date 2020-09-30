package com.televisivo.web.event;

import org.springframework.context.ApplicationEvent;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class RegistrarUsuarioEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;
    private UsuarioEvent usuarioEvent;

    public RegistrarUsuarioEvent(UsuarioEvent usuarioEvent) {
        super(usuarioEvent);
        this.usuarioEvent = usuarioEvent;
    }
}