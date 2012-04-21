/**
 * 
 */
package com.monstersoftwarellc.timeease.integration;

import java.util.List;

import javax.naming.AuthenticationException;
import javax.naming.ConfigurationException;

import com.monstersoftwarellc.timeease.TimeEaseSession;

/**
 * This is the interface for all connection service layers.  We will try each integration type first - then store in 
 * the database.  If there was a problem sending the entry over we will store a value that will specify the service
 * that didn't correctly integrate and we will try to integrate/update the objects. 
 * 
 * @author nicholas
 *
 */
public interface IIntegrationService<T extends IFreshbooksEntity> {
	
	/**
	 * Create new {@link IFreshbooksEntity} object.  We will add the entry to the list in the {@link TimeEaseSession} for you. 
	 * Method requires a null or 0 value object id number, should not exist on the server.
	 * @param item
	 * @return true if we were able to successfully store or send entry. 
	 * @throws ConfigurationException 
	 * @throws IllegalStateException 
	 * @throws AuthenticationException 
	 */
	abstract boolean create(T item) throws AuthenticationException, IllegalStateException, ConfigurationException;
	
	/**
	 * Get {@link IFreshbooksEntity} object by id.  Will attempt to find object by looking in memory,
	 * disk, and server(if online); respectively. If we had to go to the server we will store this new
	 * task on disk, but not update the {@link TimeEaseSession}.
	 * @param id
	 * @return
	 * @throws ConfigurationException 
	 * @throws IllegalStateException 
	 * @throws AuthenticationException 
	 */
	abstract T getById(int id) throws AuthenticationException, IllegalStateException, ConfigurationException;
	
	/**
	 * Update {@link IFreshbooksEntity} object. This method will always require a valid object id number.
	 * @param item
	 * @return
	 * @throws ConfigurationException 
	 * @throws IllegalStateException 
	 * @throws AuthenticationException 
	 */
	abstract boolean update(T item) throws AuthenticationException, IllegalStateException, ConfigurationException;
	
	/**
	 * Delete {@link IFreshbooksEntity} object.
	 * @param item
	 * @return
	 */
	abstract T delete(T item);

	/**
	 * Get a list of {@link IFreshbooksEntity} objects. If we are not online we will only return what is saved 
	 * to the xml file. If we get online to gather the list of items we <b>WILL</b> save to xml. Right now only called during sync.
	 * We will remove billed entries if the {@link UserSettings} field says so.
	 * <br/><br/>
	 * @return
	 * @throws ConfigurationException 
	 * @throws IllegalStateException 
	 * @throws AuthenticationException 
	 */
	abstract List<T> getAll() throws AuthenticationException, IllegalStateException, ConfigurationException;
}
