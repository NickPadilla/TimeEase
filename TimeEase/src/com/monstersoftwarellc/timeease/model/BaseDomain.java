/**
 * 
 */
package com.monstersoftwarellc.timeease.model;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.monstersoftwarellc.timeease.model.impl.Account;
import com.monstersoftwarellc.timeease.service.ISecurityService;
import com.monstersoftwarellc.timeease.service.impl.ServiceLocator;

/**
 * @author nicholas
 *
 */
@MappedSuperclass
public abstract class BaseDomain extends AbstractModelInput {

	private static final long serialVersionUID = 4579977381813175101L;

	public abstract void setCreatedBy(Account createdBy);
	public abstract void setLastModifiedBy(Account createdBy);
	public abstract void setCreatedDate(Date createdDate);
	public abstract void setLastModifiedDate(Date lastModifiedDate);	
	
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
