package com.televisivo.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Episodio.class)
public abstract class Episodio_ {

	public static volatile SingularAttribute<Episodio, Integer> numero;
	public static volatile ListAttribute<Episodio, Elenco> elencos;
	public static volatile SingularAttribute<Episodio, Temporada> temporada;
	public static volatile SingularAttribute<Episodio, String> nome;
	public static volatile SingularAttribute<Episodio, Long> id;
	public static volatile SingularAttribute<Episodio, String> enredo;
	public static volatile SingularAttribute<Episodio, Integer> duracao;
	public static volatile SingularAttribute<Episodio, Float> avaliacao;
	public static volatile ListAttribute<Episodio, Usuario> usuarios;

	public static final String NUMERO = "numero";
	public static final String ELENCOS = "elencos";
	public static final String TEMPORADA = "temporada";
	public static final String NOME = "nome";
	public static final String ID = "id";
	public static final String ENREDO = "enredo";
	public static final String DURACAO = "duracao";
	public static final String AVALIACAO = "avaliacao";
	public static final String USUARIOS = "usuarios";

}

