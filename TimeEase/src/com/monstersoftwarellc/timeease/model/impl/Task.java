/**
 * 
 */
package com.monstersoftwarellc.timeease.model.impl;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.eclipse.persistence.annotations.Index;
import org.hibernate.validator.constraints.NotEmpty;

import com.monstersoftwarellc.timeease.integration.IFreshbooksEntity;
import com.monstersoftwarellc.timeease.model.BaseDomain;

/**
 * This class outlines the Task object specification, implements {@link IFreshbooksEntity}.
 * @author nick
 *
 */
@XmlRootElement(name="task")
@Entity
public class Task extends BaseDomain implements IFreshbooksEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4013871169235173001L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index
	private Account createdBy;

	@ManyToOne(fetch=FetchType.LAZY)
	@Index
	private Account lastModifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;

	private Integer externalId;

	@NotEmpty
	private String name;

	private boolean billable;

	private Double rate;

	private String description;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Account getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Account createdBy) {
		this.createdBy = createdBy;
	}

	public Account getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(final Account lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	/**
	 * @param name the name to set
	 */
	@XmlElement(name="name")
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param externalId the externalId to set
	 */
	public void setExternalId(Integer externalId) {
		this.externalId = externalId;
	}

	/**
	 * @return the externalId
	 */
	@XmlElement(name="task_id")
	public Integer getExternalId() {
		return externalId;
	}

	/**
	 * @return the billable
	 */
	@XmlElement(name="billable")
	public boolean getBillable() {
		return billable;
	}

	/**
	 * @param billable the billable to set
	 */
	public void setBillable(boolean billable) {
		this.billable = billable;
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

	/* (non-Javadoc)
	 * @see timeease.model.AbstractFreshbooksEntity#getFreshbooksObjectName()
	 */
	@Override
	public String getFreshbooksObjectName() {
		return "task";
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
		return "Task";
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.timeease.enums.ILabel#getLabel()
	 */
	@Override
	public String getLabel() {
		return getName() + " : " + getDescription();
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
		if(!(obj instanceof Task)){
			return false;
		}
		Task task = (Task) obj;
		
		return new EqualsBuilder()
								.append(task.getId(), getId())
								.isEquals();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(9, 31)
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
