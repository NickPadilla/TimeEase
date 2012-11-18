/**
 * 
 */
package com.monstersoftwarellc.timeease.model;

import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.annotations.Index;

import com.monstersoftwarellc.timeease.model.impl.Account;
import com.monstersoftwarellc.timeease.service.ISecurityService;
import com.monstersoftwarellc.timeease.service.ServiceLocator;

/**
 * @author nicholas
 *
 */
@MappedSuperclass
public abstract class BaseDomain implements java.io.Serializable, IModelObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4579977381813175101L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch=FetchType.LAZY)
	@Index
	protected Account createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date createdDate;

	@ManyToOne(fetch=FetchType.LAZY)
	@Index
	protected Account lastModifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date lastModifiedDate;


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

	public Date getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Account getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(final Account lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	@PrePersist
	public void updateAuditInfoPersist() {
		setCreatedBy(ServiceLocator.locateCurrent(ISecurityService.class).getCurrentlyLoggedInUser());
		setCreatedDate(new Date());
	}

	@PreUpdate
	public void updateAuditInfoUpdate() {
		setLastModifiedBy(ServiceLocator.locateCurrent(ISecurityService.class).getCurrentlyLoggedInUser());
		setLastModifiedDate(new Date());
	}

}
