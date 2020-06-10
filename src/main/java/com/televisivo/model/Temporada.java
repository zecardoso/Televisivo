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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@SequenceGenerator(name = "temporada_sequence", sequenceName = "temporada_sequence", initialValue = 1, allocationSize = 1)
public class Temporada implements Serializable {

    private static final long serialVersionUID = 8837111332892294543L;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "temporada_sequence")
    @Column(name = "temporada_id")
    private Long id;

    @NotNull(message = "O número deve ser informado.")
	@Column(nullable = false)
    private int numero;

    @NotNull(message = "O ano deve ser informado.")
	@Column(nullable = false)
    private int ano;

    private Float avaliacao;

    @Column(name = "qtd_episodios")
    private int qtdEpisodios;

    @OneToMany(mappedBy = "temporada", fetch = FetchType.LAZY)
    private List<Episodio> episodios = new ArrayList<>();

    @ManyToOne(targetEntity = Serie.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "serie_id", nullable = false, referencedColumnName = "serie_id", foreignKey = @ForeignKey(name = "FK_SERIE_TEMPORADA"))
    private Serie serie;
}