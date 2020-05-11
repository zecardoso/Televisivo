package com.televisivo.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Artista.class)
public abstract class Artista_ {

	public static volatile ListAttribute<Artista, Serie> series;
	public static volatile SingularAttribute<Artista, Elenco> elenco;
	public static volatile ListAttribute<Artista, Categoria> categorias;
	public static volatile SingularAttribute<Artista, String> nome;
	public static volatile SingularAttribute<Artista, Long> id;

	public static final String SERIES = "series";
	public static final String ELENCO = "elenco";
	public static final String CATEGORIAS = "categorias";
	public static final String NOME = "nome";
	public static final String ID = "id";

}

