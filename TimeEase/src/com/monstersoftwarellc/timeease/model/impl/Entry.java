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
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.monstersoftwarellc.timeease.integration.IFreshbooksEntity;
import com.monstersoftwarellc.timeease.model.AbstractModelInput;
import com.monstersoftwarellc.timeease.utility.TimeUtil;
import com.sun.istack.internal.NotNull;


/**
 * This class outlines the Entry object specification, implements {@link IFreshbooksEntity}.
 * @author nick
 *
 */
@XmlRootElement(name="time_entry")
@Entity
public class Entry extends AbstractModelInput implements IFreshbooksEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6352279788556738179L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private Long externalId;

	private String notes;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;

	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Project project;

	@ManyToOne(fetch=FetchType.LAZY)
	private Task task;

	@Temporal(TemporalType.TIMESTAMP)
	private Date entryDate;
	
	private Double hours;
	
	private Staff staff;
	
	private Integer billed;
	
	private Boolean dirty;

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
	public void setExternalId(Long externalId) {
		this.externalId = externalId;
	}

	/**
	 * @return the externalId
	 */
	@XmlElement(name="time_entry_id")
	public Long getExternalId() {
		return externalId;
	}

	/**
	 * @return the notes
	 */
	@XmlElement(name="notes")
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return the startTime
	 */
	@XmlTransient
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	@XmlTransient
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the project
	 */
	@XmlElement(name="project_id")
	public Project getProject() {
		return project;
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(Project projectId) {
		this.project = projectId;
	}

	/**
	 * @return the taskId
	 */
	@XmlElement(name="task_id")
	public Task getTask() {
		return task;
	}

	/**
	 * @param taskId the taskId to set
	 */
	public void setTask(Task task) {
		this.task = task;
	}

	/**
	 * @return the entryDate
	 */
	@XmlElement(name="date")
	public Date getEntryDate() {
		return entryDate;
	}

	/**
	 * @param entryDate the entryDate to set
	 */
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	/**
	 * @param hours the hours to set
	 */
	public void setHours(Double hours) {
		this.hours = hours;
	}

	/**
	 * @return the hours
	 */
	@XmlElement(name="hours")
	public Double getHours() {
		return hours;
	}

	/**
	 * @param staff the staff to set
	 */
	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	/**
	 * @return the staff
	 */
	@XmlElement(name="staff_id")
	public Staff getStaff() {
		return staff;
	}

	/**
	 * @param billed the billed to set
	 */
	public void setBilled(Integer billed) {
		this.billed = billed;
	}

	/**
	 * @return the billed
	 */
	@XmlElement(name="billed")
	public Integer getBilled() {
		return billed;
	}

	/**
	 * @return the dirty
	 */
	@XmlElement(name="dirty")
	public Boolean isDirty() {
		return dirty;
	}

	/**
	 * @param dirty the dirty to set
	 */
	public void setDirty(Boolean dirty) {
		this.dirty = dirty;
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
		return "time_entry";
	}

	/* (non-Javadoc)
	 * @see timeease.model.AbstractFreshbooksEntity#getFreshbooksObjectId()
	 */
	@Override
	public Integer getFreshbooksObjectId() {
		return externalId.intValue();
	}
	
	/* (non-Javadoc)
	 * @see timeease.model.IFreshbooksEntity#getFreshbooksObjectLabel()
	 */
	@Override
	public String getFreshbooksObjectLabel() {
		return "Time Entry";
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.timeease.enums.ILabel#getLabel()
	 */
	@Override
	public String getLabel() {
		return TimeUtil.formatDate(getEntryDate().getTime()) + " " + TimeUtil.getFormatedDurationForDouble(getHours());
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
		if(!(obj instanceof Entry)){
			return false;
		}
		Entry entry = (Entry) obj;
		
		return new EqualsBuilder()
								.append(entry.getId(), getId())
								.isEquals();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(5, 31)
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
