package com.televisivo.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Elenco.class)
public abstract class Elenco_ {

	public static volatile SingularAttribute<Elenco, Artista> artista;
	public static volatile ListAttribute<Elenco, Serie> series;
	public static volatile ListAttribute<Elenco, Episodio> episodios;
	public static volatile SingularAttribute<Elenco, String> personagem;
	public static volatile SingularAttribute<Elenco, Long> id;
	public static volatile SingularAttribute<Elenco, Boolean> ator;
	public static volatile SingularAttribute<Elenco, Boolean> diretor;

	public static final String ARTISTA = "artista";
	public static final String SERIES = "series";
	public static final String EPISODIOS = "episodios";
	public static final String PERSONAGEM = "personagem";
	public static final String ID = "id";
	public static final String ATOR = "ator";
	public static final String DIRETOR = "diretor";

}

