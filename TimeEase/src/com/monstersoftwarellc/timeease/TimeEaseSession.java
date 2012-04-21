/**
 * 
 */
package com.monstersoftwarellc.timeease;

import com.monstersoftwarellc.timeease.model.impl.ApplicationSettings;

/**
 * @author nick
 *
 */
public class TimeEaseSession {

	private boolean online;
	
	private StatusLineContribution statusLineContribution;
	
	private ApplicationSettings applicationSettings;
	
	private static TimeEaseSession session;
	
	private TimeEaseSession(){}
	
	public static TimeEaseSession getCurrentInstance(){
		if(session == null){
			session = new TimeEaseSession();
		}
		return session;
	}
	
	/**
	 * @return the online
	 */
	public boolean isOnline() {
		return online;
	}

	/**
	 * @param online the online to set
	 */
	public void setOnline(boolean online) {
		this.online = online;
		// online changed, fire status line change
		if(statusLineContribution != null){
			statusLineContribution.getListener().handleEvent(null);
		}
	}

	/**
	 * @return the statusLineContribution
	 */
	public StatusLineContribution getStatusLineContribution() {
		return statusLineContribution;
	}

	/**
	 * @param statusLineContribution the statusLineContribution to set
	 */
	public void setStatusLineContribution(StatusLineContribution statusLineContribution) {
		this.statusLineContribution = statusLineContribution;
	}

	/**
	 * @return the applicationSettings
	 */
	public ApplicationSettings getApplicationSettings() {
		return applicationSettings;
	}

	/**
	 * @param applicationSettings the applicationSettings to set
	 */
	public void setApplicationSettings(ApplicationSettings applicationSettings) {
		this.applicationSettings = applicationSettings;
	}

}
