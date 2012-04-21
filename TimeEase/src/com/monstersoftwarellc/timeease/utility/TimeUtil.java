package com.monstersoftwarellc.timeease.utility;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	
	public static double getDuration(int countInSeconds){
		BigDecimal countInSecondsBD = new BigDecimal(Integer.toString(countInSeconds));
		BigDecimal duration = countInSecondsBD.divide(new BigDecimal("3600"), 4, RoundingMode.UP);
		return duration.floatValue();
	}
	
	public static Date getCalendarFromDateString(String dateString) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
			date = new Date();
		}
		return date;
	}

	public static String formatDate(Long startTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(startTime);
		return format.format(date);
	}

	public static Double getDuration(long startTime, long endTime) {
		long millsDifference = endTime - startTime;
		return getDuration((int) (millsDifference/1000));
	}
	
	/**
	 * Will return a string that represents the counter, in seconds,
	 * in the format of hh:mm:ss.
	 * @return
	 */
	public static String getTimerStringFromCounter(int counter) {
		int hours = counter / 3600;
		int remainder = counter % 3600;
		int minutes = remainder / 60;
		int seconds = remainder % 60;

		String time = (hours < 10 ? "0" : "") + hours
				+ ":" + (minutes < 10 ? "0" : "") + minutes
				+ ":" + (seconds< 10 ? "0" : "") + seconds;
		return time;
	}
	
	/**
	 * Pass in a duration, in double format, and this method will return a
	 * string with the general duration timer format.
	 * @param duration
	 * @return
	 */
	public static String getFormatedDurationForDouble(double duration){
		int totalSeconds = getSecondsFromDoubleDuration(duration);
		return getTimerStringFromCounter(totalSeconds);
	}

	/**
	 * Will pass back the number of seconds in the provided duration decimal value.
	 * @param duration
	 * @return
	 */
	public static int getSecondsFromDoubleDuration(double duration) {
		// get actual data 
		int hours = (int) duration;
		double minutesRemainder = (duration - hours) * 60;
		int minutes = (int) minutesRemainder;
		double secondsRemainder = (minutesRemainder - minutes) * 60;
		int seconds = (int) secondsRemainder;
		
		int totalSeconds = 0;
		// hours
		totalSeconds += (hours*60)*60;
		// minutes
		totalSeconds += minutes*60;
		// seconds
		totalSeconds += seconds;
		return totalSeconds;
	}

}
