package com.televisivo.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PersistenceTokenLogin.class)
public abstract class PersistenceTokenLogin_ {

	public static volatile SingularAttribute<PersistenceTokenLogin, String> series;
	public static volatile SingularAttribute<PersistenceTokenLogin, Date> lestUsed;
	public static volatile SingularAttribute<PersistenceTokenLogin, String> username;
	public static volatile SingularAttribute<PersistenceTokenLogin, String> token;

	public static final String SERIES = "series";
	public static final String LEST_USED = "lestUsed";
	public static final String USERNAME = "username";
	public static final String TOKEN = "token";

}

