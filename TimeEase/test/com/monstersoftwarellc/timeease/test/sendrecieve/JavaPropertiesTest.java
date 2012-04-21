/**
 * 
 */
package com.monstersoftwarellc.timeease.test.sendrecieve;

import java.util.Map;

/**
 * @author nicholas
 *
 */
public class JavaPropertiesTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// show all java properties.
		for (Map.Entry<Object, Object> e : System.getProperties().entrySet()) {
			System.out.println(e);
		}
	}

}
