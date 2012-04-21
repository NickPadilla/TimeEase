/**
 * 
 */
package com.monstersoftwarellc.timeease.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;


/**
 * @author nicholas
 *
 */
public class TimeEaseLicenseUtility {
	private static String PREFIX = ".";
	
	private static String SUFFIX = ".got";
	
	private static String DIRECTORY = System.getProperty("java.io.tmpdir");

	/**
	 * 
	 * @return
	 */
	public static boolean pastThirtyDayTrialPeriod(){
		boolean pastThrityDays = false;
		FilenameFilter fileTypeFilter = FileUtility.createFileTypeFilter(new String[]{SUFFIX});
		File[] files = FileUtility.listFiles(DIRECTORY, fileTypeFilter);
		Date today = new Date();
		if(files != null && files.length > 0){
			for(File file : files){
				String dateInLong = file.getName().replace(SUFFIX, "");
				// remove '.' that makes a file hidden in Linux and Mac
				dateInLong = dateInLong.replace(PREFIX, "");
				Date date = new Date(Long.parseLong(dateInLong));
				Calendar installDate = Calendar.getInstance();
				installDate.setTime(date);
				installDate.add(Calendar.MONTH, 1);
				if(today.after(installDate.getTime())){
					pastThrityDays = true;	
				}
			}
		}else{
			// no file found, create new file			
			try {
				File file = new File(DIRECTORY, PREFIX + Long.toString(today.getTime()) + SUFFIX);
				FileOutputStream fout = new FileOutputStream(file);
				PrintWriter writer = new PrintWriter(fout);
				writer.close();
				fout.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return pastThrityDays;
	}
	
	
	public static boolean isLicenseValid(){
		
		return false;
	}

}
