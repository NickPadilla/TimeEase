package com.monstersoftwarellc.timeease.model.impl;

import com.monstersoftwarellc.timeease.model.enums.BillingMethodType;
import com.monstersoftwarellc.timeease.model.impl.Account;
import com.monstersoftwarellc.timeease.model.impl.Client;
import com.monstersoftwarellc.timeease.model.impl.Staff;
import com.monstersoftwarellc.timeease.model.impl.Task;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.4.1.v20121003-rNA", date="2012-11-18T21:10:27")
@StaticMetamodel(Project.class)
public class Project_ { 

    public static volatile SingularAttribute<Project, List> budgetHours;
    public static volatile SingularAttribute<Project, Long> id;
    public static volatile ListAttribute<Project, Staff> staff;
    public static volatile SingularAttribute<Project, BillingMethodType> billingMethodType;
    public static volatile ListAttribute<Project, Task> projectTasks;
    public static volatile SingularAttribute<Project, Double> rate;
    public static volatile SingularAttribute<Project, Client> client;
    public static volatile SingularAttribute<Project, Double> projectHourBudget;
    public static volatile SingularAttribute<Project, String> description;
    public static volatile SingularAttribute<Project, String> name;
    public static volatile SingularAttribute<Project, Account> account;
    public static volatile SingularAttribute<Project, Integer> externalId;

}