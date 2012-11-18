/**
 * 
 */
package com.monstersoftwarellc.timeease.widgets;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;

import com.pfcomponents.common.widgets.ButtonEx;

/**
 * @author navid
 * FIXME: remove fix the button size calculations for parent and give to vendor.
 */
public class ButtonHack extends ButtonEx {

	/**
	 * @param parent
	 * @param style
	 */
	public ButtonHack(Composite parent, int style) {
		super(parent, style);
	}

	/* (non-Javadoc)
	 * @see com.pfcomponents.common.widgets.ButtonEx#computeSize(int, int, boolean)
	 */
	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		return new Point(70, 25);
	}

	
	
}
