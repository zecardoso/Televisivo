package com.televisivo.model;

import com.televisivo.model.enumerate.Genero;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Usuario.class)
public abstract class Usuario_ {

	public static volatile SingularAttribute<Usuario, Date> lastLogin;
	public static volatile SingularAttribute<Usuario, String> password;
	public static volatile SingularAttribute<Usuario, Boolean> ativo;
	public static volatile SingularAttribute<Usuario, Genero> genero;
	public static volatile ListAttribute<Usuario, Role> roles;
	public static volatile ListAttribute<Usuario, Categoria> categorias;
	public static volatile ListAttribute<Usuario, Episodio> episodios;
	public static volatile SingularAttribute<Usuario, String> nome;
	public static volatile SingularAttribute<Usuario, Long> id;
	public static volatile SingularAttribute<Usuario, String> sobrenome;
	public static volatile SingularAttribute<Usuario, String> email;
	public static volatile SingularAttribute<Usuario, String> username;

	public static final String LAST_LOGIN = "lastLogin";
	public static final String PASSWORD = "password";
	public static final String ATIVO = "ativo";
	public static final String GENERO = "genero";
	public static final String ROLES = "roles";
	public static final String CATEGORIAS = "categorias";
	public static final String EPISODIOS = "episodios";
	public static final String NOME = "nome";
	public static final String ID = "id";
	public static final String SOBRENOME = "sobrenome";
	public static final String EMAIL = "email";
	public static final String USERNAME = "username";

}

