package com.monstersoftwarellc.timeease.model.impl;

import com.monstersoftwarellc.timeease.model.BaseDomain_;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-11-20T19:16:47.573-0700")
@StaticMetamodel(Entry.class)
public class Entry_ extends BaseDomain_ {
	public static volatile SingularAttribute<Entry, Long> id;
	public static volatile SingularAttribute<Entry, Account> createdBy;
	public static volatile SingularAttribute<Entry, Account> lastModifiedBy;
	public static volatile SingularAttribute<Entry, Date> createdDate;
	public static volatile SingularAttribute<Entry, Date> lastModifiedDate;
	public static volatile SingularAttribute<Entry, Long> externalId;
	public static volatile SingularAttribute<Entry, String> notes;
	public static volatile SingularAttribute<Entry, Date> startTime;
	public static volatile SingularAttribute<Entry, Date> endTime;
	public static volatile SingularAttribute<Entry, Project> project;
	public static volatile SingularAttribute<Entry, Task> task;
	public static volatile SingularAttribute<Entry, Date> entryDate;
	public static volatile SingularAttribute<Entry, Double> hours;
	public static volatile SingularAttribute<Entry, Staff> staff;
	public static volatile SingularAttribute<Entry, Integer> billed;
	public static volatile SingularAttribute<Entry, Boolean> dirty;
}
