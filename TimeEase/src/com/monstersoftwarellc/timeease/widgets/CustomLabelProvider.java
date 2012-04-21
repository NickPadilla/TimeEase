/**
 * 
 */
package com.monstersoftwarellc.timeease.widgets;

import org.eclipse.jface.viewers.LabelProvider;

import com.monstersoftwarellc.timeease.enums.ILabel;

/**
 * @author nicholas
 *
 */
public class CustomLabelProvider extends LabelProvider {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		String text = "";
		if(element instanceof ILabel){
			text = ((ILabel)element).getLabel();
		}else{
			throw new IllegalStateException("CustomLabelProvider is unable to handle object type, not an instance of ILabeled! Object : " 
					+ element.toString());
		}
		return text;
	}

}
