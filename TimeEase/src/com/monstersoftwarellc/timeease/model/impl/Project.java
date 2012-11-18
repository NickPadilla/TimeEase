/**
 * 
 */
package com.monstersoftwarellc.timeease.model.impl;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.monstersoftwarellc.timeease.integration.IFreshbooksEntity;
import com.monstersoftwarellc.timeease.model.AbstractModelInput;
import com.monstersoftwarellc.timeease.model.enums.BillingMethodType;
import com.sun.istack.internal.NotNull;

/**
 * This class outlines the Project object specification, implements {@link IFreshbooksEntity}.
 * @author nick
 *
 */
@XmlRootElement(name="project")
@Entity
public class Project extends AbstractModelInput implements IFreshbooksEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6466778023904593087L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private Integer externalId;
	
	private String name;
	
	@Enumerated(EnumType.STRING)
	private BillingMethodType billingMethodType;
	
	private Client client;
	
	private Double rate;
	
	private String description;
	
	private Double projectHourBudget;
	
	@ManyToMany(fetch=FetchType.LAZY)
	private List<Task> projectTasks;
	
	@OneToMany(fetch=FetchType.LAZY)
	private List<Staff> staff;
	
	private List<Double> budgetHours;

	@ManyToOne(fetch=FetchType.LAZY)
	@NotNull
	private Account account;
	

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	
	/**
	 * @param externalId the externalId to set
	 */
	public void setExternalId(Integer projectId) {
		this.externalId = projectId;
	}


	/**
	 * @return the externalId
	 */
	@XmlElement(name="project_id")
	public Integer getExternalId() {
		return externalId;
	}

	/**
	 * @return the name
	 */
	@XmlElement(name="name")
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the billingMethodType
	 */
	@XmlElement(name="bill_method")
	public BillingMethodType getBillingMethodType() {
		return billingMethodType;
	}

	/**
	 * @param billingMethodType the billingMethodType to set
	 */
	public void setBillingMethodType(BillingMethodType billingMethodType) {
		this.billingMethodType = billingMethodType;
	}

	/**
	 * @return the client
	 */
	@XmlElement(name="client_id")
	public Client getClient() {
		return client;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(Client client) {
		this.client = client;
	}

	/**
	 * @return the rate
	 */
	@XmlElement(name="rate", defaultValue="0")
	public Double getRate() {
		return rate;
	}

	/**
	 * @param rate the rate to set
	 */
	public void setRate(Double rate) {
		this.rate = rate;
	}

	/**
	 * @return the description
	 */
	@XmlElement(name="description")
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the projectHourBudget
	 */
	@XmlElement(name="hour_budget", defaultValue="0")
	public Double getProjectHourBudget() {
		return projectHourBudget;
	}

	/**
	 * @param projectHourBudget the projectHourBudget to set
	 */
	public void setProjectHourBudget(Double projectHourBudget) {
		this.projectHourBudget = projectHourBudget;
	}

	/**
	 * @return the projectTasks
	 */
	@XmlElementWrapper(name="tasks")
	@XmlElement(name="task")
	public List<Task> getProjectTasks() {
		return projectTasks;
	}

	/**
	 * @param projectTasks the projectTasks to set
	 */
	public void setProjectTasks(List<Task> projectTasks) {
		this.projectTasks = projectTasks;
	}

	/**
	 * @param staff the staff to set
	 */
	public void setStaff(List<Staff> staff) {
		this.staff = staff;
	}

	/**
	 * @return the staff
	 */
	@XmlElementWrapper(name="staff")
	@XmlElements({
        @XmlElement(name="staff"),
        @XmlElement(name="staff_id")
     })
	public List<Staff> getStaff() {
		return staff;
	}

	/**
	 * <b>This field will only ever contain one element in the list<br/>
	 * I did this for the sake of less POJO's.</b>
	 * @param budgetHours the budgetHours to set
	 */
	public void setBudgetHours(List<Double> newBudgetHours) {
		this.budgetHours = newBudgetHours;
	}

	/**
	 * <b>This field will only ever contain one element in the list<br/>
	 * I did this for the sake of less POJO's.</b>
	 * @return the budgetHours
	 */
	@XmlElementWrapper(name="budget")
	@XmlElement(name="hours")
	public List<Double> getBudgetHours() {
		return budgetHours;
	}

	/**
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(Account user) {
		this.account = user;
	}

	/* (non-Javadoc)
	 * @see timeease.model.AbstractFreshbooksEntity#getFreshbooksObjectName()
	 */
	@Override
	public String getFreshbooksObjectName() {
		return "project";
	}

	/* (non-Javadoc)
	 * @see timeease.model.AbstractFreshbooksEntity#getFreshbooksObjectId()
	 */
	@Override
	public Integer getFreshbooksObjectId() {
		return externalId;
	}

	/* (non-Javadoc)
	 * @see timeease.model.IFreshbooksEntity#getFreshbooksObjectLabel()
	 */
	@Override
	public String getFreshbooksObjectLabel() {
		return "Project";
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.timeease.enums.ILabel#getLabel()
	 */
	@Override
	public String getLabel() {
		return getName() + ((getDescription() != null && !getDescription().isEmpty()) ? " : " + getDescription() : "");
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// performance optimization
		if(obj == this){
			return true;
		}
		if(!(obj instanceof Project)){
			return false;
		}
		Project project = (Project) obj;
		
		return new EqualsBuilder()
								.append(project.getId(), getId())
								.isEquals();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(3, 31)
								.append(getId())
								.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE, true);
	}
	
}
