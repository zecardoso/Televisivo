package com.televisivo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@SequenceGenerator(name = "elenco_sequence", sequenceName = "elenco_sequence", initialValue = 1, allocationSize = 1)
public class Elenco implements Serializable {

    private static final long serialVersionUID = -2236280201631903989L;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "elenco_sequence")
    @Column(name = "elenco_id")
    private Long id;

    private boolean diretor = false;

    private boolean ator = false;

    @Size(min = 3, max = 40, message = "O nome deve ter entre {min} e {max} caracteres.")
	@NotBlank(message = "O personagem deve ser informado.")
	@NotNull(message = "O personagem deve ser informado.")
	@Column(length = 40, nullable = false)
    private String personagem;

    @ManyToMany(mappedBy = "elencos")
    private List<Episodio> episodios = new ArrayList<>();

    @ManyToMany(mappedBy = "elencos")
    private List<Serie> series = new ArrayList<>();

    @OneToOne(targetEntity = Artista.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "artista_id", referencedColumnName = "artista_id")
    private Artista artista;
}