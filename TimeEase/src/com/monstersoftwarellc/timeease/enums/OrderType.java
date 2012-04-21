/**
 * 
 */
package com.monstersoftwarellc.timeease.enums;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


/**
 * @author nicholas
 *
 */
public enum OrderType implements IEnum {
	
	ASC("Ascending Order"),
	DESC("Descending Order");
	
	private String label;
	
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	
	private OrderType(String label){
		this.label = label;
	}
	
	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.goldrush.ILabel#getLabel()
	 */
	@Override
	public String getLabel(){
		return label;
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.goldrush.enums.IEnum#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.goldrush.enums.IEnum#addPropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.goldrush.enums.IEnum#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.goldrush.enums.IEnum#removePropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(propertyName,
				listener);
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.goldrush.enums.IEnum#firePropertyChange(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	public void firePropertyChange(String propertyName, Object oldValue,
			Object newValue) {
		propertyChangeSupport.firePropertyChange(propertyName, oldValue,
				newValue);
	}
}