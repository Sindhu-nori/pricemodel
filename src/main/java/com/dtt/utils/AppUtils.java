package com.dtt.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

public class AppUtils {
	
	private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	public static final DateTimeFormatter DEFAULT_TIME_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	public static String generateUniqueId() {
		//UUID uuid = UUID.randomUUID();
		//return uuid.toString();
		
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	public static String getDate() {
        SimpleDateFormat smpdate = new SimpleDateFormat(DEFAULT_PATTERN);
        Date date = new Date();
        return smpdate.format(date);
    }
	
	 // ✅ Get current date-time in system zone
    public static LocalDateTime getCurrentDateTime() {
    	//DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_PATTERN);
       // return LocalDateTime.now().format(formatter);
        return LocalDateTime.now();
    }
    
    public static String getDateAndTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_PATTERN);
        return LocalDateTime.now().format(formatter);
    }
    
    public static LocalDateTime parseDateTime(String dateTime) {
        if (dateTime == null || dateTime.trim().isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(dateTime, DEFAULT_TIME_PATTERN);
    }

}
