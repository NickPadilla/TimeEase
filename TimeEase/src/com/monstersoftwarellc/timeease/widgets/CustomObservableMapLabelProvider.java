/**
 * 
 */
package com.monstersoftwarellc.timeease.widgets;

import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;

import com.monstersoftwarellc.timeease.enums.ILabel;


/**
 * @author nicholas
 *
 */
public class CustomObservableMapLabelProvider extends ObservableMapLabelProvider {


	/**
	 * @param attributeMaps
	 */
	public CustomObservableMapLabelProvider(IObservableMap[] attributeMaps) {
		super(attributeMaps);
	}

	/**
	 * @param attributeMap
	 */
	public CustomObservableMapLabelProvider(IObservableMap attributeMap) {
		super(attributeMap);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider#getColumnText(java.lang.Object, int)
	 */
	@Override
	public String getColumnText(Object element, int columnIndex) {
		String text = "";
		if (columnIndex < attributeMaps.length) {
			Object result = attributeMaps[columnIndex].get(element);
			if(result instanceof ILabel){
				text = ((ILabel)result).getLabel();
			}else{
				text = result.toString();
			}
		}
		return text;
	}

}
