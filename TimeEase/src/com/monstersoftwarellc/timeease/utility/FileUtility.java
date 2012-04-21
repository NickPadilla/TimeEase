/**
 * 
 */
package com.monstersoftwarellc.timeease.utility;

import java.io.File;
import java.io.FilenameFilter;

/**
 * @author nicholas
 *
 */
public class FileUtility {

	/**
	 * @param dirPath
	 * @param filter
	 * @return
	 */
	public static File[] listFiles(String dirPath, FilenameFilter filter) {
		File dir = new File(dirPath);
		if (dir.exists()) {
			return dir.listFiles(filter);
		}
		return null;
	}

	/**
	 * @param extensions
	 * @return
	 */
	public static FilenameFilter createFileTypeFilter(final String[] extensions) {
		FilenameFilter fileNameFilter = new FilenameFilter() {
			/* (non-Javadoc)
			 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
			 */
			@Override
			public boolean accept(File dir, String name) {
				for (String extension: extensions) {
					if (name.endsWith(extension.trim())) {
						return true;
					}
				}
				return false;
			}
		};

		return fileNameFilter;
	}


}
