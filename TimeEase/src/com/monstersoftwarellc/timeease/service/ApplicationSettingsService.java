/**
 * 
 */
package com.monstersoftwarellc.timeease.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monstersoftwarellc.timeease.model.impl.ApplicationSettings;

/**
 * @author nicholas
 *
 */
@Service
public class ApplicationSettingsService implements IApplicationSettingsService {
	
	@Autowired
	private ISecurityService securityService;

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.goldrush.service.IApplicationSettingsService#getApplicationSettings()
	 */
	@Override
	public ApplicationSettings getApplicationSettings() {
		// each user will have its own settings - we will ensure we get the single instance from the logged in user.
		return securityService.getCurrentlyLoggedInUser().getSettings();
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.goldrush.service.IApplicationSettingsService#getNumberOfItemsToShowPerPage()
	 */
	@Override
	public int getNumberOfItemsToShowPerPage() {
		return getApplicationSettings().getNumberOfItemsToShowPerPage();
	}

}
