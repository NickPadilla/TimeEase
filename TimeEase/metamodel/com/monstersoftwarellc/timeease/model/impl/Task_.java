package com.monstersoftwarellc.timeease.model.impl;

import com.monstersoftwarellc.timeease.model.impl.Account;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.4.1.v20121003-rNA", date="2012-11-19T12:52:02")
@StaticMetamodel(Task.class)
public class Task_ { 

    public static volatile SingularAttribute<Task, Long> id;
    public static volatile SingularAttribute<Task, Double> rate;
    public static volatile SingularAttribute<Task, String> description;
    public static volatile SingularAttribute<Task, String> name;
    public static volatile SingularAttribute<Task, Account> account;
    public static volatile SingularAttribute<Task, Integer> externalId;
    public static volatile SingularAttribute<Task, Boolean> billable;

}