package com.monstersoftwarellc.timeease.model.impl;

import com.monstersoftwarellc.timeease.model.BaseDomain_;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-11-20T19:16:47.671-0700")
@StaticMetamodel(Task.class)
public class Task_ extends BaseDomain_ {
	public static volatile SingularAttribute<Task, Long> id;
	public static volatile SingularAttribute<Task, Account> createdBy;
	public static volatile SingularAttribute<Task, Account> lastModifiedBy;
	public static volatile SingularAttribute<Task, Date> createdDate;
	public static volatile SingularAttribute<Task, Date> lastModifiedDate;
	public static volatile SingularAttribute<Task, Integer> externalId;
	public static volatile SingularAttribute<Task, String> name;
	public static volatile SingularAttribute<Task, Boolean> billable;
	public static volatile SingularAttribute<Task, Double> rate;
	public static volatile SingularAttribute<Task, String> description;
}
