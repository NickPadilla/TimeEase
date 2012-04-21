/**
 * 
 */
package com.monstersoftwarellc.timeease.utility;

/**
 * @author nicholas
 *
 */
public class DisplayUtility {

	public static String getParentLabel(String freshbooksObjectLabel) {
		String ret = "";
		if(freshbooksObjectLabel.endsWith("y")){
			ret = freshbooksObjectLabel.substring(0, freshbooksObjectLabel.length()-1) + "ies";
		}else{
			ret = freshbooksObjectLabel + "s";
		}
		return ret;
	}

}
