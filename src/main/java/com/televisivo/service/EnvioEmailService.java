package com.televisivo.service;

import com.televisivo.util.dto.Mensagem;

public interface EnvioEmailService {
    
    void enviar(Mensagem mensagem);
}
