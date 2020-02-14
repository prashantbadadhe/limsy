package com.radixile.limsy.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserAdditional.class)
public abstract class UserAdditional_ {

	public static volatile SingularAttribute<UserAdditional, Location> address;
	public static volatile SingularAttribute<UserAdditional, String> gender;
	public static volatile SingularAttribute<UserAdditional, String> phone;
	public static volatile SingularAttribute<UserAdditional, Long> id;
	public static volatile SingularAttribute<UserAdditional, LocalDate> birthDate;
	public static volatile SingularAttribute<UserAdditional, String> email;

}

