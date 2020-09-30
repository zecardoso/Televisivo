package com.televisivo.web.listener;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.televisivo.service.EnvioEmailService;
import com.televisivo.service.RegistrarUsuarioService;
import com.televisivo.util.dto.Mensagem;
import com.televisivo.web.event.RegistrarUsuarioEvent;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class UsuarioRegistradoListener implements ApplicationListener<RegistrarUsuarioEvent> {

    @Autowired
    private EnvioEmailService envioEmailService;

    @Autowired
    private RegistrarUsuarioService registrarUsuarioService;

    @Override
    public void onApplicationEvent(RegistrarUsuarioEvent event) {
        confirmarCadastroUsuario(event);
    }

    private void confirmarCadastroUsuario(RegistrarUsuarioEvent event) {
        String token = UUID.randomUUID().toString();
        registrarUsuarioService.criaTokenVerificacaoUsuario(event.getUsuarioEvent().getUsuario(), token);
        formatarMensagem(event, token);
    }

    private void formatarMensagem(RegistrarUsuarioEvent event, String token) {
        Mensagem mensagem = new Mensagem();
        String url = event.getUsuarioEvent().getUrl()+"/registrar/confirmar-registro-usuario?token"+token;
        mensagem.setAssunto("Confirme seu cadastro.");
        mensagem.setCorpo("email_verification.html");
        Set<String> destinatarios = new HashSet<>();
        destinatarios.add(event.getUsuarioEvent().getUsuario().getEmail());
        mensagem.setDestinatarios(destinatarios);
        Map<String, Object> variaveis = new HashedMap<>();
        variaveis.put("usuario", event.getUsuarioEvent().getUsuario());
        variaveis.put("url", url);
        mensagem.setVariaveis(variaveis);
        enviarMensagemEmail(mensagem);
    }

    private void enviarMensagemEmail(Mensagem mensagem) {
        envioEmailService.enviar(mensagem);
    }
}