package com.monstersoftwarellc.timeease.model;

import com.monstersoftwarellc.timeease.model.impl.Account;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.4.1.v20121003-rNA", date="2012-11-18T21:10:27")
@StaticMetamodel(BaseDomain.class)
public abstract class BaseDomain_ { 

    public static volatile SingularAttribute<BaseDomain, Long> id;
    public static volatile SingularAttribute<BaseDomain, Account> createdBy;
    public static volatile SingularAttribute<BaseDomain, Account> lastModifiedBy;
    public static volatile SingularAttribute<BaseDomain, Date> lastModifiedDate;
    public static volatile SingularAttribute<BaseDomain, Date> createdDate;

}