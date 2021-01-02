package com.televisivo.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer{

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path serieUploadDir = Paths.get("./serie-imagem");
        String serieUploadPath = serieUploadDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/serie-imagem/**").addResourceLocations("file:/" + serieUploadPath + "/");
    }
    
}
