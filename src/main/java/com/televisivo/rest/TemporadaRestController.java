package com.televisivo.rest;

import java.util.List;

import javax.validation.Valid;

import com.televisivo.model.Temporada;
import com.televisivo.service.TemporadaService;
import com.televisivo.service.exceptions.NegocioException;
import com.televisivo.service.exceptions.TemporadaNaoCadastradaException;

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
@RequestMapping(value = "/api/temporada")
public class TemporadaRestController {

    @Autowired
    private TemporadaService temporadaService;

    @GetMapping(value = "/listar")
    public List<Temporada> listar() {
        return temporadaService.findAll();
    }

    @PostMapping(value = "/adicionar")
    @ResponseStatus(HttpStatus.OK)
    public Temporada registrar(@RequestBody @Valid Temporada temporada) {
        return temporadaService.save(temporada);
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<Temporada> alterar(@PathVariable Long id, @RequestBody Temporada temporada) {
        try {
            Temporada temporada2 = temporadaService.findById(id);
            if (temporada2 != null) {
                BeanUtils.copyProperties(temporada, temporada2);
                temporada2 = temporadaService.update(temporada2);
                return ResponseEntity.ok(temporada2);
            }
        } catch (TemporadaNaoCadastradaException e) {
            throw new NegocioException("O temporada não existe no sistema");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        temporadaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping(value = "/buscar/{id}")
    public ResponseEntity<Temporada> buscar(@PathVariable Long id) {
        Temporada temporada = temporadaService.findById(id);
        if (temporada != null) {
            return ResponseEntity.ok(temporada);
        }
        return ResponseEntity.notFound().build();
    }
}