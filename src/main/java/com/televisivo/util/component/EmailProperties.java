package com.televisivo.util.component;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties("api.email")
public class EmailProperties {
    
    private String remetente;
}
