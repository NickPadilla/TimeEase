package com.monstersoftwarellc.timeease.model.impl;

import com.monstersoftwarellc.timeease.model.BaseDomain_;
import com.monstersoftwarellc.timeease.model.enums.ClientStatus;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-11-20T19:16:47.516-0700")
@StaticMetamodel(Client.class)
public class Client_ extends BaseDomain_ {
	public static volatile SingularAttribute<Client, Long> id;
	public static volatile SingularAttribute<Client, Account> createdBy;
	public static volatile SingularAttribute<Client, Account> lastModifiedBy;
	public static volatile SingularAttribute<Client, Date> createdDate;
	public static volatile SingularAttribute<Client, Date> lastModifiedDate;
	public static volatile SingularAttribute<Client, Integer> externalId;
	public static volatile SingularAttribute<Client, String> firstName;
	public static volatile SingularAttribute<Client, String> lastName;
	public static volatile SingularAttribute<Client, String> organization;
	public static volatile SingularAttribute<Client, String> email;
	public static volatile SingularAttribute<Client, String> username;
	public static volatile SingularAttribute<Client, String> password;
	public static volatile SingularAttribute<Client, String> homePhone;
	public static volatile SingularAttribute<Client, String> workPhone;
	public static volatile SingularAttribute<Client, String> cellPhone;
	public static volatile SingularAttribute<Client, String> fax;
	public static volatile SingularAttribute<Client, String> language;
	public static volatile SingularAttribute<Client, String> currencyCode;
	public static volatile SingularAttribute<Client, String> notes;
	public static volatile SingularAttribute<Client, String> primaryAddress;
	public static volatile SingularAttribute<Client, String> primaryAddress2;
	public static volatile SingularAttribute<Client, String> primaryCity;
	public static volatile SingularAttribute<Client, String> primaryState;
	public static volatile SingularAttribute<Client, String> primaryCountry;
	public static volatile SingularAttribute<Client, String> primaryZipCode;
	public static volatile SingularAttribute<Client, String> secondaryAddress;
	public static volatile SingularAttribute<Client, String> secondaryAddress2;
	public static volatile SingularAttribute<Client, String> secondaryCity;
	public static volatile SingularAttribute<Client, String> secondaryState;
	public static volatile SingularAttribute<Client, String> secondaryCountry;
	public static volatile SingularAttribute<Client, String> secondaryZipCode;
	public static volatile SingularAttribute<Client, String> vatName;
	public static volatile SingularAttribute<Client, Integer> vatNumber;
	public static volatile SingularAttribute<Client, Double> credit;
	public static volatile SingularAttribute<Client, String> url;
	public static volatile SingularAttribute<Client, String> clientUrl;
	public static volatile SingularAttribute<Client, String> lastModified;
	public static volatile SingularAttribute<Client, ClientStatus> clientStatus;
	public static volatile SingularAttribute<Client, Contact> contact;
	public static volatile ListAttribute<Client, Contact> contacts;
}
