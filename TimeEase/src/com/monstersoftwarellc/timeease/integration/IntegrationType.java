/**
 * 
 */
package com.monstersoftwarellc.timeease.integration;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.monstersoftwarellc.timeease.enums.IEnum;

/**
 * @author nicholas
 *
 */
public enum IntegrationType implements IEnum {
	
	FRESHBOOKS("FreshBooks"),
	JIRA("Jira"),
	QUICKBOOKS("QuickBooks");
	
	private String label;
	
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	
	IntegrationType(String label){
		this.label = label;
	}

	@Override
	public String getLabel(){
		return label;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(propertyName,
				listener);
	}

	public void firePropertyChange(String propertyName, Object oldValue,
			Object newValue) {
		propertyChangeSupport.firePropertyChange(propertyName, oldValue,
				newValue);
	}
}
