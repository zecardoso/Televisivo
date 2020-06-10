package com.televisivo.rest.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.televisivo.service.exceptions.NegocioException;
import com.televisivo.service.exceptions.RoleNaoCadastradaException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class TelevisivoExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(RoleNaoCadastradaException.class)
    public ResponseEntity<?> handlerRoleNaoCadastrada(RoleNaoCadastradaException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
        String detail = ex.getMessage();
        Problem problem = createBuilderProblem(status, problemType, detail).addUserMessage("O registro não foi localizado no sistema").build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handlerNegocioException(NegocioException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemType problemType = ProblemType.ERRO_NEGOCIO;
        String detail = ex.getMessage();
        Problem problem = createBuilderProblem(status, problemType, detail).addUserMessage(detail).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ProblemType problemType = ProblemType.DADOS_INVALIDOS;
        String detail = "Um ou mais campos estão invalidos. Faça o preenchimneto correto e tente novamente";
        BindingResult bindingResult = ex.getBindingResult();
        List<Fields> fields = bindingResult.getFieldErrors()
                                           .stream()
                                           .map(fieldError -> {
                                               String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
                                               Fields field = new Fields();
                                               field.setNome(fieldError.getField());
                                               field.setUserMessage(message);
                                               return field;
                                            })
                                            .collect(Collectors.toList());
        Problem problem = createBuilderProblem(status, problemType, detail).addUserMessage(detail).addListFields(fields).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (body == null) {
            body = Problem.builder()
                          .addTimestamp(LocalDateTime.now())
                          .addStatus(status.value())
                          .addTitle(status.getReasonPhrase())
                          .addUserMessage("Ocorreu um erro interno inesperado no sistema. Tente novamente e se o problema persistir, entre em contato com o adminstrador.")
                          .build();
        } else if (body instanceof String) {
            body = Problem.builder()
                          .addTimestamp(LocalDateTime.now())
                          .addStatus(status.value())
                          .addTitle(status.getReasonPhrase())
                          .addUserMessage("Ocorreu um erro interno inesperado no sistema. Tente novamente e se o problema persistir, entre em contato com o adminstrador.")
                          .build();
        }
        return super.handleExceptionInternal(ex, body, new HttpHeaders(), status, request);
    }

    private Problem.Builder createBuilderProblem(HttpStatus httpStatus, ProblemType problemType, String detail) {
        return Problem.builder()
				      .addTimestamp(LocalDateTime.now())
				      .addStatus(httpStatus.value())
				      .addType(problemType.getUri())
				      .addTitle(problemType.getTitle())
				      .addDetail(detail);
    }
}