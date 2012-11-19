package com.monstersoftwarellc.timeease.model.impl;

import com.monstersoftwarellc.timeease.model.impl.Account;
import com.monstersoftwarellc.timeease.model.impl.Project;
import com.monstersoftwarellc.timeease.model.impl.Staff;
import com.monstersoftwarellc.timeease.model.impl.Task;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.4.1.v20121003-rNA", date="2012-11-19T12:52:02")
@StaticMetamodel(Entry.class)
public class Entry_ { 

    public static volatile SingularAttribute<Entry, Task> task;
    public static volatile SingularAttribute<Entry, Double> hours;
    public static volatile SingularAttribute<Entry, Date> endTime;
    public static volatile SingularAttribute<Entry, Date> entryDate;
    public static volatile SingularAttribute<Entry, Long> externalId;
    public static volatile SingularAttribute<Entry, Date> startTime;
    public static volatile SingularAttribute<Entry, Long> id;
    public static volatile SingularAttribute<Entry, Integer> billed;
    public static volatile SingularAttribute<Entry, Project> project;
    public static volatile SingularAttribute<Entry, Staff> staff;
    public static volatile SingularAttribute<Entry, Boolean> dirty;
    public static volatile SingularAttribute<Entry, Account> account;
    public static volatile SingularAttribute<Entry, String> notes;

}