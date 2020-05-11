package com.televisivo.rest;

import java.util.List;

import javax.validation.Valid;

import com.televisivo.model.Serie;
import com.televisivo.service.SerieService;
import com.televisivo.service.exceptions.NegocioException;
import com.televisivo.service.exceptions.SerieNaoCadastradaException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/series")
public class SerieRestController {

    @Autowired
    private SerieService serieService;

    @PostMapping(value = "/adicionar")
    @ResponseStatus(HttpStatus.OK)
    public Serie registrar(@RequestBody @Valid Serie serie) {
        return serieService.adicionar(serie);
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<Serie> alterar(@PathVariable Long id, @RequestBody Serie serie) {
        try {
            Serie serie2 = serieService.buscarId(id);
            if (serie2 != null) {
                BeanUtils.copyProperties(serie, serie2);
                serie2 = serieService.alterar(serie2);
                return ResponseEntity.ok(serie2);
            }
        } catch (SerieNaoCadastradaException e) {
            throw new NegocioException("O serie não existe no sistema");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        Serie serie = serieService.buscarId(id);
        serieService.remover(serie);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping(value = "/buscar/{id}")
    public ResponseEntity<Serie> buscar(@PathVariable Long id) {
        Serie serie = serieService.buscarId(id);
        if (serie != null) {
            return ResponseEntity.ok(serie);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/listar")
    public List<Serie> listar() {
        return serieService.listar();
    }
}