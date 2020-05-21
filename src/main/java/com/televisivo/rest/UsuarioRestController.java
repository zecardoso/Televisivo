package com.televisivo.rest;

import java.util.List;

import javax.validation.Valid;

import com.televisivo.model.Usuario;
import com.televisivo.service.UsuarioService;
import com.televisivo.service.exceptions.NegocioException;
import com.televisivo.service.exceptions.UsuarioNaoCadastradoException;

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
@RequestMapping(value = "/api/usuario")
public class UsuarioRestController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping(value = "/listar")
    public List<Usuario> listar() {
        return usuarioService.findAll();
    }

    @PostMapping(value = "/adicionar")
    @ResponseStatus(HttpStatus.OK)
    public Usuario registrar(@RequestBody @Valid Usuario usuario) {
        return usuarioService.save(usuario);
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<Usuario> alterar(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            Usuario usuario2 = usuarioService.findById(id);
            if (usuario2 != null) {
                BeanUtils.copyProperties(usuario, usuario2);
                usuario2 = usuarioService.update(usuario2);
                return ResponseEntity.ok(usuario2);
            }
        } catch (UsuarioNaoCadastradoException e) {
            throw new NegocioException("O usuario não existe no sistema");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping(value = "/buscar/{id}")
    public ResponseEntity<Usuario> buscar(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.notFound().build();
    }
}