package com.televisivo.rest;

import java.util.List;

import javax.validation.Valid;

import com.televisivo.model.Permissao;
import com.televisivo.service.PermissaoService;
import com.televisivo.service.exceptions.NegocioException;
import com.televisivo.service.exceptions.PermissaoNaoCadastradaException;

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
@RequestMapping(value = "/api/permissao")
public class PermissaoRestController {

    @Autowired
    private PermissaoService permissaoService;

    @GetMapping(value = "/listar")
    public List<Permissao> listar() {
        return permissaoService.findAll();
    }

    @PostMapping(value = "/adicionar")
    @ResponseStatus(HttpStatus.OK)
    public Permissao registrar(@RequestBody @Valid Permissao permissao) {
        return permissaoService.save(permissao);
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<Permissao> alterar(@PathVariable Long id, @RequestBody Permissao permissao) {
        try {
            Permissao permissao2 = permissaoService.findById(id);
            if (permissao2 != null) {
                BeanUtils.copyProperties(permissao, permissao2);
                permissao2 = permissaoService.update(permissao2);
                return ResponseEntity.ok(permissao2);
            }
        } catch (PermissaoNaoCadastradaException e) {
            throw new NegocioException("O permissao não existe no sistema");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        permissaoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping(value = "/buscar/{id}")
    public ResponseEntity<Permissao> buscar(@PathVariable Long id) {
        Permissao permissao = permissaoService.findById(id);
        if (permissao != null) {
            return ResponseEntity.ok(permissao);
        }
        return ResponseEntity.notFound().build();
    }
}