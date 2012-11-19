/**
 * 
 */
package com.monstersoftwarellc.timeease.model;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.config.SessionCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.descriptors.DescriptorEvent;
import org.eclipse.persistence.descriptors.DescriptorEventAdapter;
import org.eclipse.persistence.sessions.Session;

import com.monstersoftwarellc.timeease.model.impl.Account;
import com.monstersoftwarellc.timeease.service.ISecurityService;
import com.monstersoftwarellc.timeease.service.impl.ServiceLocator;


/**
 * The changes made within the 'aboutTo..' methods do not get cached.  This causes the queries to return a stale object.
 * I have turned off the shared eclipselink cache for now do to this problem.  Need to investigate further. 
 * @author nicholas
 *
 */
public class AuditorAwareImpl extends DescriptorEventAdapter implements SessionCustomizer, DescriptorCustomizer  {
	
	private static final Logger LOG = Logger.getLogger(AuditorAwareImpl.class);

	/* (non-Javadoc)
	 * @see org.eclipse.persistence.config.DescriptorCustomizer#customize(org.eclipse.persistence.descriptors.ClassDescriptor)
	 */
	public void customize(ClassDescriptor descriptor) {
		descriptor.getEventManager().addListener(this);
	}

	/**
	 * @param session
	 */
	public void customize(Session session) {
		for (ClassDescriptor descriptor : session.getDescriptors().values()) {
			customize(descriptor);
		}
	}


	/* (non-Javadoc)
	 * @see org.eclipse.persistence.descriptors.DescriptorEventAdapter#aboutToInsert(org.eclipse.persistence.descriptors.DescriptorEvent)
	 */
	@SuppressWarnings("unchecked")
	public void aboutToInsert(DescriptorEvent event) {
		// Had to use this method of service retrieval due to EclipseLink registering this class before
		// Spring had a chance to load it and wire it up, this works.
		Account user = null;
		try{
			ISecurityService securityService = ServiceLocator.locateCurrent(ISecurityService.class);	
			user = securityService.getCurrentlyLoggedInUser();			
		}catch (Exception e) {
			// should only have problems during testing
			LOG.error("Something Went Wrong In the Auditor Implementation - Disregard if Testing or Application Performed Initial DB Creation");
		}
		for (String table : (List<String>)event.getDescriptor().getTableNames()) {
			if(user != null){
				event.getRecord().put(table + ".CREATEDBY_ID", user.getId());
			}
			event.getRecord().put(table + ".CREATEDDATE", new Date());
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.persistence.descriptors.DescriptorEventAdapter#aboutToUpdate(org.eclipse.persistence.descriptors.DescriptorEvent)
	 */
	@SuppressWarnings("unchecked")
	public void aboutToUpdate(DescriptorEvent event) {
		// Had to use this method of service retrieval due to EclipseLink registering this class before
		// Spring had a chance to load it and wire it up, this works.
		Account user = null;
		try{
			ISecurityService securityService = ServiceLocator.locateCurrent(ISecurityService.class);	
			user = securityService.getCurrentlyLoggedInUser();
		}catch (Exception e) {
			// should only have problems during testing
			LOG.error("Something Went Wrong In the Auditor Implementation - Disregard if Testing or Application Performed Initial DB Creation");
		}
		for (String table : (List<String>)event.getDescriptor().getTableNames()) {
			if(user != null){
				event.getRecord().put(table + ".LASTMODIFIEDBY_ID", user.getId());
			}
			// always set the modified dates even if we can't set the last modified ID.
			event.getRecord().put(table + ".LASTMODIFIEDDATE", new Date());
		}
	}
	
	
}
