package com.televisivo.web.event;

import org.springframework.context.ApplicationEvent;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class RecuperarSenhaUsuarioEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    private UsuarioEvent usuarioEvent;

    public RecuperarSenhaUsuarioEvent(UsuarioEvent usuarioEvent) {
        super(usuarioEvent);
        this.usuarioEvent = usuarioEvent;
    }
}
