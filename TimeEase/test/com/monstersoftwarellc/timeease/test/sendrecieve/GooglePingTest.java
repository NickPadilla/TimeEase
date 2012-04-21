/**
 * 
 */
package com.monstersoftwarellc.timeease.test.sendrecieve;

import com.monstersoftwarellc.timeease.utility.ConnectionUtility;

/**
 * @author nicholas
 *
 */
public class GooglePingTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Are we online? : " + ConnectionUtility.areWeOnline());
	}

}
