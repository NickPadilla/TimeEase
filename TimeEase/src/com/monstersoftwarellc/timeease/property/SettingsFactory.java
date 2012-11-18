/**
 * 
 */
package com.monstersoftwarellc.timeease.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** 
 * 
 * @author nicholas
 *
 */
@Service("settingsFactory")
public class SettingsFactory {
	
	@Autowired
	private IPropertyService propertyService;

	/**
	 * Gets the application properties that are used for this instance, which is the first one in db.
	 * The application properties object is created when the db is first loaded.
	 * @return
	 */
	public IApplicationSettings getApplicationSettings() {
		return propertyService.getProperties(IApplicationSettings.class);
	}


}
