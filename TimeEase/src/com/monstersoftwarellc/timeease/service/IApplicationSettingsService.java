/**
 * 
 */
package com.monstersoftwarellc.timeease.service;

import com.monstersoftwarellc.timeease.model.impl.ApplicationSettings;

/**
 * @author nicholas
 *
 */
public interface IApplicationSettingsService { 
	
	
	/**
	 * Gets the application properties that are used for this instance, which is the first one in db.
	 * The application properties object is created when the db is first loaded.
	 * @return
	 */
	public abstract ApplicationSettings getApplicationSettings();

	/**
	 * This method will return the number of items to show on a page.
	 * @return
	 */
	public abstract int getNumberOfItemsToShowPerPage();

}
