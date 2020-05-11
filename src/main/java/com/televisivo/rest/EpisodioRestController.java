package com.televisivo.rest;

import java.util.List;

import javax.validation.Valid;

import com.televisivo.model.Episodio;
import com.televisivo.service.EpisodioService;
import com.televisivo.service.exceptions.NegocioException;
import com.televisivo.service.exceptions.EpisodioNaoCadastradoException;

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
@RequestMapping(value = "/episodios")
public class EpisodioRestController {

    @Autowired
    private EpisodioService episodioService;

    @PostMapping(value = "/adicionar")
    @ResponseStatus(HttpStatus.OK)
    public Episodio registrar(@RequestBody @Valid Episodio episodio) {
        return episodioService.adicionar(episodio);
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<Episodio> alterar(@PathVariable Long id, @RequestBody Episodio episodio) {
        try {
            Episodio episodio2 = episodioService.buscarId(id);
            if (episodio2 != null) {
                BeanUtils.copyProperties(episodio, episodio2);
                episodio2 = episodioService.alterar(episodio2);
                return ResponseEntity.ok(episodio2);
            }
        } catch (EpisodioNaoCadastradoException e) {
            throw new NegocioException("O episodio não existe no sistema");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        Episodio episodio = episodioService.buscarId(id);
        episodioService.remover(episodio);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping(value = "/buscar/{id}")
    public ResponseEntity<Episodio> buscar(@PathVariable Long id) {
        Episodio episodio = episodioService.buscarId(id);
        if (episodio != null) {
            return ResponseEntity.ok(episodio);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/listar")
    public List<Episodio> listar() {
        return episodioService.listar();
    }
}