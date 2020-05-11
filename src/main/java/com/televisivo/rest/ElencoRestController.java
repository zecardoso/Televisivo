package com.televisivo.rest;

import java.util.List;

import javax.validation.Valid;

import com.televisivo.model.Elenco;
import com.televisivo.service.ElencoService;
import com.televisivo.service.exceptions.NegocioException;
import com.televisivo.service.exceptions.ElencoNaoCadastradoException;

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
@RequestMapping(value = "/elencos")
public class ElencoRestController {

    @Autowired
    private ElencoService elencoService;

    @PostMapping(value = "/adicionar")
    @ResponseStatus(HttpStatus.OK)
    public Elenco registrar(@RequestBody @Valid Elenco elenco) {
        return elencoService.adicionar(elenco);
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<Elenco> alterar(@PathVariable Long id, @RequestBody Elenco elenco) {
        try {
            Elenco elenco2 = elencoService.buscarId(id);
            if (elenco2 != null) {
                BeanUtils.copyProperties(elenco, elenco2);
                elenco2 = elencoService.alterar(elenco2);
                return ResponseEntity.ok(elenco2);
            }
        } catch (ElencoNaoCadastradoException e) {
            throw new NegocioException("O elenco não existe no sistema");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        Elenco elenco = elencoService.buscarId(id);
        elencoService.remover(elenco);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping(value = "/buscar/{id}")
    public ResponseEntity<Elenco> buscar(@PathVariable Long id) {
        Elenco elenco = elencoService.buscarId(id);
        if (elenco != null) {
            return ResponseEntity.ok(elenco);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/listar")
    public List<Elenco> listar() {
        return elencoService.listar();
    }
}