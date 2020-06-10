package com.televisivo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@SequenceGenerator(name = "escopo_sequence", sequenceName = "escopo_sequence", initialValue = 1, allocationSize = 1)
public class Escopo implements Serializable {

    private static final long serialVersionUID = 2460652453480654609L;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "escopo_sequence")
    @Column(name = "escopo_id")
    private Long id;

    @Size(min = 3, max = 40, message = "O nome deve ter entre {min} e {max} caracteres.")
	@NotBlank(message = "O nome deve ser informado.")
	@NotNull(message = "O nome deve ser informado.")
	@Column(length = 40, nullable = false)
    private String nome;
}