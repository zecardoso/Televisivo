package com.televisivo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@SequenceGenerator(name = "serie_sequence", sequenceName = "serie_sequence", initialValue = 1, allocationSize = 1)
public class Serie implements Serializable {

    private static final long serialVersionUID = 776430409375361602L;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "serie_sequence")
    @Column(name = "serie_id")
    private Long id;

    @Size(min = 3, max = 40, message = "O nome deve ter entre {min} e {max} caracteres.")
	@NotBlank(message = "O nome deve ser informado.")
	@NotNull(message = "O nome deve ser informado.")
	@Column(length = 40, nullable = false)
    private String nome;

    @Size(min = 10, max = 500, message = "O enredo deve ter entre {min} e {max} caracteres.")
	@NotBlank(message = "O enredo deve ser informado.")
	@NotNull(message = "O enredo deve ser informado.")
	@Column(length = 500, nullable = false)
    private String enredo;

    @NotNull(message = "O ano deve ser informado.")
	@Column(nullable = false)
    private int ano;

    @NotNull(message = "A restrição de idade deve ser informada.")
	@Column(nullable = false)
    private int restricao;

    @Column(name = "qtd_temporadas")
    private int qtdTemporadas;

    @Column(name = "qtd_seguidores")
    private int qtdSeguidores;

    @OneToMany(mappedBy = "serie", fetch = FetchType.LAZY)
    private List<Temporada> temporadas = new ArrayList<>();

    @ManyToOne(targetEntity = Servico.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "servico_id", nullable = false, referencedColumnName = "servico_id", foreignKey = @ForeignKey(name = "FK_SERVICO_SERIE"))
    private Servico servico;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "serie_categoria", joinColumns = @JoinColumn(name = "serie_id"), inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    private List<Categoria> categorias = new ArrayList<>();

    @JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "serie_elenco", joinColumns = @JoinColumn(name = "serie_id"), inverseJoinColumns = @JoinColumn(name = "elenco_id"))
    private List<Elenco> elencos = new ArrayList<>();

    @JsonIgnore
	@ManyToMany(mappedBy="series")
    private List<Artista> artistas = new ArrayList<>();
}