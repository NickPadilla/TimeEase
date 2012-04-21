/**
 * 
 */
package com.monstersoftwarellc.timeease.utility;

import org.eclipse.swt.graphics.RGB;

/**
 * @author navid
 *
 */
public class RGBUtil {

	/**
	 * Returns an RGB where all colors are set to the color provided
	 * @param color
	 * @return
	 */
	public static RGB all(int color){
		return new RGB(color,color,color);
	}
	
	public static RGB white(){
		return new RGB(255,255,255);
	}
	
	public static RGB black(){
		return new RGB(0,0,0);
	}

}
