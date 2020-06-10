package com.televisivo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Embeddable
public class Auditoria implements Serializable {

    private static final long serialVersionUID = 5568752960016113809L;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_operacao", nullable = false, columnDefinition = "DATE")
    private Date dataOperacao;

    @Column(name = "tipo_operacao", nullable = false)
    private String tipoOperacao;

    @PrePersist
    @PreUpdate
    public void onCreate() {
        this.setDataOperacao(new Date());
    }
}