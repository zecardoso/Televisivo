package com.televisivo.rest;

import java.util.List;

import javax.validation.Valid;

import com.televisivo.model.Escopo;
import com.televisivo.service.EscopoService;
import com.televisivo.service.exceptions.NegocioException;
import com.televisivo.service.exceptions.EscopoNaoCadastradoException;

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
@RequestMapping(value = "/escopos")
public class EscopoRestController {

    @Autowired
    private EscopoService escopoService;

    @PostMapping(value = "/adicionar")
    @ResponseStatus(HttpStatus.OK)
    public Escopo registrar(@RequestBody @Valid Escopo escopo) {
        return escopoService.adicionar(escopo);
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<Escopo> alterar(@PathVariable Long id, @RequestBody Escopo escopo) {
        try {
            Escopo escopo2 = escopoService.buscarId(id);
            if (escopo2 != null) {
                BeanUtils.copyProperties(escopo, escopo2);
                escopo2 = escopoService.alterar(escopo2);
                return ResponseEntity.ok(escopo2);
            }
        } catch (EscopoNaoCadastradoException e) {
            throw new NegocioException("O escopo não existe no sistema");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        Escopo escopo = escopoService.buscarId(id);
        escopoService.remover(escopo);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping(value = "/buscar/{id}")
    public ResponseEntity<Escopo> buscar(@PathVariable Long id) {
        Escopo escopo = escopoService.buscarId(id);
        if (escopo != null) {
            return ResponseEntity.ok(escopo);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/listar")
    public List<Escopo> listar() {
        return escopoService.listar();
    }
}