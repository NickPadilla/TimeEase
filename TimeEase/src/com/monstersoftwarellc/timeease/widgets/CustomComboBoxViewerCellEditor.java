/**
 * 
 */
package com.monstersoftwarellc.timeease.widgets;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.swt.widgets.Composite;

/**
 * @author nicholas
 * 
 */
public class CustomComboBoxViewerCellEditor extends ComboBoxViewerCellEditor {

	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	/**
	 * @param parent
	 * @param style
	 */
	public CustomComboBoxViewerCellEditor(Composite parent, int style) {
		super(parent, style);
		setCustomDefaults();
	}

	/**
	 * @param parent
	 */
	public CustomComboBoxViewerCellEditor(Composite parent) {
		super(parent);
		setCustomDefaults();
	}

	/**
	 * 
	 */
	private void setCustomDefaults() {
		setContentProvider(ArrayContentProvider.getInstance());
		setLabelProvider(new CustomLabelProvider());
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

	protected void firePropertyChange(String propertyName, Object oldValue,
			Object newValue) {
		propertyChangeSupport.firePropertyChange(propertyName, oldValue,
				newValue);
	}

}
