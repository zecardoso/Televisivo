package com.televisivo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@SequenceGenerator(name = "categoria_sequence", sequenceName = "categoria_sequence", initialValue = 1, allocationSize = 1)
public class Categoria implements Serializable {

    private static final long serialVersionUID = -495061248786573017L;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categoria_sequence")
    @Column(name = "categoria_id")
    private Long id;

    @Size(min = 3, max = 40, message = "O nome deve ter entre {min} e {max} caracteres.")
	@NotBlank(message = "O nome deve ser informado.")
	@NotNull(message = "O nome deve ser informado.")
	@Column(length = 40, nullable = false)
    private String nome;

    @JsonIgnore
	@ManyToMany(mappedBy="categorias")
    private List<Usuario> usuarios = new ArrayList<>();

    @JsonIgnore
	@ManyToMany(mappedBy="categorias")
    private List<Serie> series = new ArrayList<>();

    @JsonIgnore
	@ManyToMany(mappedBy="categorias")
    private List<Artista> artistas = new ArrayList<>();
}