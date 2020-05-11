package com.televisivo.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Categoria.class)
public abstract class Categoria_ {

	public static volatile ListAttribute<Categoria, Artista> artistas;
	public static volatile ListAttribute<Categoria, Serie> series;
	public static volatile SingularAttribute<Categoria, String> nome;
	public static volatile SingularAttribute<Categoria, Long> id;
	public static volatile ListAttribute<Categoria, Usuario> usuarios;

	public static final String ARTISTAS = "artistas";
	public static final String SERIES = "series";
	public static final String NOME = "nome";
	public static final String ID = "id";
	public static final String USUARIOS = "usuarios";

}

