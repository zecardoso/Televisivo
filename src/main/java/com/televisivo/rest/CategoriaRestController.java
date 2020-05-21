package com.televisivo.rest;

import java.util.List;

import javax.validation.Valid;

import com.televisivo.model.Categoria;
import com.televisivo.service.CategoriaService;
import com.televisivo.service.exceptions.CategoriaNaoCadastradaException;
import com.televisivo.service.exceptions.NegocioException;

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
@RequestMapping(value = "/api/categoria")
public class CategoriaRestController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping(value = "/listar")
    public List<Categoria> listar() {
        return categoriaService.findAll();
    }

    @PostMapping(value = "/adicionar")
    @ResponseStatus(HttpStatus.OK)
    public Categoria registrar(@RequestBody @Valid Categoria categoria) {
        return categoriaService.save(categoria);
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<Categoria> alterar(@PathVariable Long id, @RequestBody Categoria categoria) {
        try {
            Categoria categoria2 = categoriaService.findById(id);
            if (categoria2 != null) {
                BeanUtils.copyProperties(categoria, categoria2);
                categoria2 = categoriaService.update(categoria2);
                return ResponseEntity.ok(categoria2);
            }
        } catch (CategoriaNaoCadastradaException e) {
            throw new NegocioException("O categoria não existe no sistema");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        categoriaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping(value = "/buscar/{id}")
    public ResponseEntity<Categoria> buscar(@PathVariable Long id) {
        Categoria categoria = categoriaService.findById(id);
        if (categoria != null) {
            return ResponseEntity.ok(categoria);
        }
        return ResponseEntity.notFound().build();
    }
}