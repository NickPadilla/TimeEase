/**
 * 
 */
package com.monstersoftwarellc.timeease.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;




/**
 * Define model object that will need a label and tooltip when used as input.
 * @author nicholas
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractModelInput implements Serializable, IModelObject {
	
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(String propertyName,PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(String propertyName,PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(propertyName,listener);
	}

	protected void firePropertyChange(String propertyName, Object oldValue,Object newValue) {
		propertyChangeSupport.firePropertyChange(propertyName, oldValue,newValue);
	}
}
