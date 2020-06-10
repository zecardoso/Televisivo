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
import javax.persistence.JoinTable;
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
@SequenceGenerator(name = "artista_sequence", sequenceName = "artista_sequence", initialValue = 1, allocationSize = 1)
public class Artista implements Serializable {

    private static final long serialVersionUID = 2386611204242179779L;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "artista_sequence")
    @Column(name = "artista_id")
    private Long id;

    @Size(min = 3, max = 40, message = "O nome deve ter entre {min} e {max} caracteres.")
	@NotBlank(message = "O nome deve ser informado.")
	@NotNull(message = "O nome deve ser informado.")
	@Column(length = 40, nullable = false)
    private String nome;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "artista_categoria", joinColumns = @JoinColumn(name = "artista_id"), inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    private List<Categoria> categorias = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "artista_serie", joinColumns = @JoinColumn(name = "artista_id"), inverseJoinColumns = @JoinColumn(name = "serie_id"))
    private List<Serie> series = new ArrayList<>();

    @OneToOne(mappedBy = "artista", fetch = FetchType.LAZY)
    private Elenco elenco;
}