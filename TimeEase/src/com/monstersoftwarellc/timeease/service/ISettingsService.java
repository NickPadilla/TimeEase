/**
 * 
 */
package com.monstersoftwarellc.timeease.service;

import com.monstersoftwarellc.timeease.model.impl.Property;
import com.monstersoftwarellc.timeease.property.IApplicationSettings;
import com.monstersoftwarellc.timeease.repository.impl.PropertyRepository;

/**
 * @author nicholas
 *
 */
public interface ISettingsService {
	
	/**
	 * Gets the application properties that are used for this instance, which is the first one in db.
	 * The application properties object is created when the db is first loaded.
	 * @return
	 */
	public abstract IApplicationSettings getApplicationSettings();
	
	public abstract Property findByName(String name);

	public abstract PropertyRepository getPropertyRepository();
}
