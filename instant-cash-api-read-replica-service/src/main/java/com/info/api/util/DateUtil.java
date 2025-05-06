package com.info.api.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

	public static java.sql.Date converToSqltDate(Date date) {
		if (date == null) {
			return null;
		}
		return new java.sql.Date(date.getTime());
	}

	
	public static Calendar convertTimestampToCalendar(Timestamp timestamp) {
		if (timestamp == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(timestamp);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}
	public static java.sql.Date converToSqltDate(Calendar calendar) {
		if (calendar == null) {
			return null;
		}
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return new java.sql.Date(calendar.getTime().getTime());
	}

	public static Calendar convertSqlDateToCalendar(java.sql.Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}

	

	public static Date convertToDate(Calendar calendar) {
		if (calendar == null) {
			return null;
		}		 
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date convertToDateWithEndTime(Calendar calendar) {
		if (calendar == null) {
			return null;
		}		 
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	public static int compareDate(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return 0;
		}		
		return date1.compareTo(date2);
	}

	public static String convertDateToString(Date date, String format) {
		if(date == null){
			return null;
		}
		DateFormat df = new SimpleDateFormat(format);
		return  df.format(date);	
	}
	
	public static String convertCalendarToString(Calendar calendar, String format) {
		if(calendar == null){
			return null;
		}
		DateFormat df = new SimpleDateFormat(format);
		return  df.format(calendar.getTime());	
	}

	public static Calendar convertDateToCalendar(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}

	public static Timestamp convertToTimestamp(Calendar calendar) {
		if(calendar == null){
			return null;
		}
		
		Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
		return timestamp;
	}

	public static int compareDate(Calendar calendar1,
			Calendar calendar2) {		
		return calendar1.compareTo(calendar2);
	}

	public static Calendar cleanTimeComponent(Calendar calendar){
		if(calendar == null){
			return null;
		}
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}
	
	public static Date cleanTimeComponent(Date date){
		if(date == null){
			return null;
		}
		date.setSeconds(0);
		date.setMinutes(0);
		date.setHours(0);
		
		return date;
	}

	public static Calendar setCurrentTimeComponent(Calendar calendar) {
		Calendar calendar2 = Calendar.getInstance();
		
		calendar.set(Calendar.HOUR_OF_DAY, calendar2.get(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar2.get(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar2.get(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar2.get(Calendar.MILLISECOND));
		
		return calendar;
	}
 
	
	
	public static java.sql.Date convertStringToSQLDate(String dateString)
	{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		if(dateString ==null)
			return null;
		else
		{
			try {
				 return converToSqltDate(sdf.parse(dateString));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
	}

	public static String currentDateTimeInUTC(String formate) {
		final SimpleDateFormat sdf = new SimpleDateFormat(formate);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		final String utcTime = sdf.format(new Date());

		return utcTime;
	}

	public static String currentDateTime(String formate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(formate);
		String dateTime = dateFormat.format(new Date());
		return dateTime;
	}

	public static String convertDateFormate(String dateString, String sourceFormat,String targetFormat) {
		String dateStr = "";
		try {
			SimpleDateFormat sourceDateFormat = new SimpleDateFormat(sourceFormat);

			SimpleDateFormat targetDateFormat = new SimpleDateFormat(targetFormat);

			Date sourceDate = sourceDateFormat.parse(dateString);
			dateStr = targetDateFormat.format(sourceDate);
		}catch (Exception e) {
			dateStr = "";
			System.err.println(e);
		}

		return dateStr;
	}

}
