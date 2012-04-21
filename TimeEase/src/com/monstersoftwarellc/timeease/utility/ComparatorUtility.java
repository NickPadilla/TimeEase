/**
 * 
 */
package com.monstersoftwarellc.timeease.utility;

import java.util.Comparator;

import com.monstersoftwarellc.timeease.model.entry.Entry;


/**
 * @author nicholas
 *
 */
public class ComparatorUtility {
	
	public static Comparator<Entry> byEntryDate(){
		return new EntryComparatorByDate();
	}
	
	private static class EntryComparatorByDate implements Comparator<Entry>{

		/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		public int compare(Entry o1, Entry o2) {
			return o1.getEntryDate().compareTo(o2.getEntryDate());
		}
		
	}

}
