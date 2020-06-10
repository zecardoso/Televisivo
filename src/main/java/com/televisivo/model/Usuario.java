package com.televisivo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.televisivo.config.TelevisivoConfig;
import com.televisivo.model.enumerate.Genero;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.annotations.WhereJoinTable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@DynamicUpdate(true)
@Data
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@SQLDelete(sql = "UPDATE usuario SET registro_deletado = true WHERE usuario_id = ?")
@Where(clause = TelevisivoConfig.NAO_DELETADO)
@WhereJoinTable(clause = TelevisivoConfig.NAO_DELETADO)
@SequenceGenerator(name = "usuario_sequence", sequenceName = "usuario_sequence", initialValue = 1, allocationSize = 1)
public class Usuario implements UserDetails {

    private static final long serialVersionUID = 3598850498230758819L;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_sequence")
    @Column(name = "usuario_id")
    private Long id;

    @Size(min = 3, max = 40, message = "O username deve ter entre {min} e {max} caracteres.")
    @NotBlank(message = "O usernome deve ser informado.")
    @NotNull(message = "O usernome deve ser informado.")
    @Column(length = 40, unique = true, nullable = false)
    private String username;

    @Size(min = 3, max = 40, message = "O nome deve ter entre {min} e {max} caracteres.")
    @NotBlank(message = "O nome deve ser informado.")
    @NotNull(message = "O nome deve ser informado.")
    @Column(length = 40, nullable = false)
    private String nome;

    @Size(min = 3, max = 70, message = "O sobrenome deve ter entre {min} e {max} caracteres.")
    @NotBlank(message = "O sobrenome deve ser informado.")
    @NotNull(message = "O sobrenome deve ser informado.")
    @Column(length = 70, nullable = false)
    private String sobrenome;

    @Size(min = 3, max = 50, message = "O e-mail deve ter entre {min} e {max} caracteres.")
    @NotBlank(message = "O e-mail deve ser informado.")
    @NotNull(message = "O e-mail deve ser informado.")
    @Column(length = 50, unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private Genero genero;

    @Column(length = 100, nullable = false)
    private String password;

    @Transient
    private String contraSenha;

    @Column(nullable = true)
    private boolean ativo = false;

    @Column(name = "registro_deletado", nullable = false, columnDefinition = "boolean default false")
    private boolean deleted = Boolean.FALSE;

    @Column(nullable = true)
    private Integer failedLogin;

    @DateTimeFormat(pattern = "dd/M/yyyy")
    @Temporal(TemporalType.DATE)
    @Column(nullable = true, columnDefinition = "DATE")
    private Date lastLogin;

    @JsonIgnore
    @Size(min = 1, message = "Selecione pelo menos um grupo")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "usuario_role", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "usuario_categoria", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    private List<Categoria> categorias;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "usuario_episodio", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "episodio_id"))
    private List<Episodio> episodios;

    @JsonIgnore
    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<GrantedAuthority> autoridade = new ArrayList<>();
        for (Role role : this.getRoles()) {
            autoridade.add(new SimpleGrantedAuthority("ROLE_" + role.getNome().toUpperCase()));
        }
        return autoridade;
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return isAtivo();
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return isAtivo();
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return isAtivo();
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return isAtivo();
    }
}