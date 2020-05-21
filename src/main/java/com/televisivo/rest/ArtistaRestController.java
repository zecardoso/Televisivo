package com.televisivo.rest;

import java.util.List;

import javax.validation.Valid;

import com.televisivo.model.Artista;
import com.televisivo.service.ArtistaService;
import com.televisivo.service.exceptions.ArtistaNaoCadastradoException;
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
@RequestMapping(value = "/api/artista")
public class ArtistaRestController {

    @Autowired
    private ArtistaService artistaService;

    @GetMapping(value = "/listar")
    public List<Artista> listar() {
        return artistaService.findAll();
    }

    @PostMapping(value = "/adicionar")
    @ResponseStatus(HttpStatus.OK)
    public Artista registrar(@RequestBody @Valid Artista artista) {
        return artistaService.save(artista);
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<Artista> alterar(@PathVariable Long id, @RequestBody Artista artista) {
        try {
            Artista artista2 = artistaService.findById(id);
            if (artista2 != null) {
                BeanUtils.copyProperties(artista, artista2);
                artista2 = artistaService.update(artista2);
                return ResponseEntity.ok(artista2);
            }
        } catch (ArtistaNaoCadastradoException e) {
            throw new NegocioException("O artista não existe no sistema");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        artistaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping(value = "/buscar/{id}")
    public ResponseEntity<Artista> buscar(@PathVariable Long id) {
        Artista artista = artistaService.findById(id);
        if (artista != null) {
            return ResponseEntity.ok(artista);
        }
        return ResponseEntity.notFound().build();
    }
}