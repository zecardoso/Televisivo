package com.televisivo.web.listener;

import com.televisivo.service.EnvioEmailService;
import com.televisivo.util.dto.Mensagem;
import com.televisivo.web.event.RegistrarUsuarioEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class UsuarioRecuperarSenhaListener implements ApplicationListener<RegistrarUsuarioEvent> {

    @Autowired
    private EnvioEmailService envioEmailService;

    @Override
    public void onApplicationEvent(RegistrarUsuarioEvent event) {
        confirmarCadastroUsuario(event);
    }

    private void confirmarCadastroUsuario(RegistrarUsuarioEvent event) {
        Mensagem mensagem = new Mensagem();
        envioEmailService.enviar(mensagem);
    }
}