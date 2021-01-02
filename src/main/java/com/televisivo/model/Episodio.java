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
import javax.persistence.Transient;
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
@SequenceGenerator(name = "episodio_sequence", sequenceName = "episodio_sequence", allocationSize = 1)
public class Episodio implements Serializable {

    private static final long serialVersionUID = 1144598121866007606L;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "episodio_sequence")
    @Column(name = "episodio_id", updatable = false)
    private Long id;

    @NotNull(message = "O número deve ser informado.")
	@Column(nullable = false)
    private int numero;

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

    private Float avaliacao;

    @NotNull(message = "A duração deve ser informada.")
	@Column(nullable = false)
    private int duracao;

    @ManyToOne(targetEntity = Temporada.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "temporada_id", nullable = false, referencedColumnName = "temporada_id", foreignKey = @ForeignKey(name = "FK_TEMPORADA_EPISODIO"))
    private Temporada temporada;

    @OneToMany(mappedBy = "episodio", fetch = FetchType.LAZY)
    private List<UsuarioEpisodio> usuarioEpisodios = new ArrayList<>();

    @Transient
    private boolean marcado;
}