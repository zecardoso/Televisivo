package com.televisivo.rest;

import java.util.List;

import javax.validation.Valid;

import com.televisivo.model.Servico;
import com.televisivo.service.ServicoService;
import com.televisivo.service.exceptions.NegocioException;
import com.televisivo.service.exceptions.ServicoNaoCadastradoException;

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
@RequestMapping(value = "/api/servico")
public class ServicoRestController {

    @Autowired
    private ServicoService servicoService;

    @GetMapping(value = "/listar")
    public List<Servico> listar() {
        return servicoService.findAll();
    }

    @PostMapping(value = "/adicionar")
    @ResponseStatus(HttpStatus.OK)
    public Servico registrar(@RequestBody @Valid Servico servico) {
        return servicoService.save(servico);
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<Servico> alterar(@PathVariable Long id, @RequestBody Servico servico) {
        try {
            Servico servico2 = servicoService.findById(id);
            if (servico2 != null) {
                BeanUtils.copyProperties(servico, servico2);
                servico2 = servicoService.update(servico2);
                return ResponseEntity.ok(servico2);
            }
        } catch (ServicoNaoCadastradoException e) {
            throw new NegocioException("O servico não existe no sistema");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        servicoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping(value = "/buscar/{id}")
    public ResponseEntity<Servico> buscar(@PathVariable Long id) {
        Servico servico = servicoService.findById(id);
        if (servico != null) {
            return ResponseEntity.ok(servico);
        }
        return ResponseEntity.notFound().build();
    }
}