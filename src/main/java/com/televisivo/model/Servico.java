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
import javax.persistence.OneToMany;
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
@SequenceGenerator(name = "servico_sequence", sequenceName = "servico_sequence", initialValue = 1, allocationSize = 1)
public class Servico implements Serializable {

    private static final long serialVersionUID = -6011359642344617832L;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "servico_sequence")
    @Column(name = "servico_id")
    private Long id;

    @Size(min = 3, max = 40, message = "O nome deve ter entre {min} e {max} caracteres.")
	@NotBlank(message = "O nome deve ser informado.")
	@NotNull(message = "O nome deve ser informado.")
	@Column(length = 40, nullable = false)
    private String nome;

    @OneToMany(mappedBy = "servico", fetch = FetchType.LAZY)
    private List<Serie> series = new ArrayList<>();
}