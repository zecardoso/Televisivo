package com.televisivo.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ErrorListening implements ErrorViewResolver {

    private static final String ERROR = "error";
    private static final String MESSAGE = "message";
    private static final String TITLE = "title";

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> map) {
        ModelAndView model = new ModelAndView("/error");
        model.addObject("status", status.value());
        switch (status.value()) {
            case 403:
                model.addObject(ERROR, "Sem permissão de acesso.");
                model.addObject(MESSAGE, "Você não está autorizado a acessar a página que solicitou.");
                model.addObject(TITLE, "Error 403 - Sem permissão de acesso.");
                break;
            case 404:
                model.addObject(ERROR, "Página não encontrada.");
                model.addObject(MESSAGE, "A url para a página '" + map.get("path") + "' não existe.");
                model.addObject(TITLE, "Error 404 - Página não encontrada.");
                break;
            case 405:
                model.addObject(ERROR, "Sem permissão.");
                model.addObject(MESSAGE, "Você não tem permissão.");
                model.addObject(TITLE, "Error 405 - Sem permissão.");
                break;
            case 500:
                model.addObject(ERROR, "Ocorreu um erro interno no servidor.");
                model.addObject(MESSAGE, "Ocorreu um erro inexperado, tente mais tarde.");
                model.addObject(TITLE, "Error 500 - Ocorreu um erro interno no servidor.");
                break;
            default:
                model.addObject(ERROR, map.get(ERROR));
                model.addObject(MESSAGE, map.get(MESSAGE));
                break;
        }
        return model;
    }
}