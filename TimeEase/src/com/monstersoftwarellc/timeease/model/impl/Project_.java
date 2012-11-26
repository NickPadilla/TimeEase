package com.monstersoftwarellc.timeease.model.impl;

import com.monstersoftwarellc.timeease.model.BaseDomain_;
import com.monstersoftwarellc.timeease.model.enums.BillingMethodType;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-11-20T19:16:47.606-0700")
@StaticMetamodel(Project.class)
public class Project_ extends BaseDomain_ {
	public static volatile SingularAttribute<Project, Long> id;
	public static volatile SingularAttribute<Project, Account> createdBy;
	public static volatile SingularAttribute<Project, Account> lastModifiedBy;
	public static volatile SingularAttribute<Project, Date> createdDate;
	public static volatile SingularAttribute<Project, Date> lastModifiedDate;
	public static volatile SingularAttribute<Project, Integer> externalId;
	public static volatile SingularAttribute<Project, String> name;
	public static volatile SingularAttribute<Project, BillingMethodType> billingMethodType;
	public static volatile SingularAttribute<Project, Client> client;
	public static volatile SingularAttribute<Project, Double> rate;
	public static volatile SingularAttribute<Project, String> description;
	public static volatile SingularAttribute<Project, Double> projectHourBudget;
	public static volatile ListAttribute<Project, Task> projectTasks;
	public static volatile ListAttribute<Project, Staff> staff;
}
