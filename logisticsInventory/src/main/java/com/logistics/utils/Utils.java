package com.logistics.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Utils {
	
	public final static String DEFAULT_DATE_FORMAT = "MM/dd/yyyy";
	public final static String FORMAT_MM_DDD_YYYY = "MMM, dd yyyy";
	
	public static long getEPOCTimeFromData(java.sql.Date sqlDate){
		if(sqlDate == null) 
			return -1;
		
		return sqlDate.getTime();
	}


	public static String getDateInMMDDYYYYFormat(java.util.Date dateValue) {
		String dateToReturn = "";
		DateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		try {
			dateToReturn = (dateValue != null) ? dateFormat.format(dateValue) : "";
		}catch(Exception exception) {
			dateToReturn = "";
		}
		return dateToReturn;
	}
	
	public static String getDateInMMDDYYYYFormat(java.sql.Date dateValue) {
		String dateToReturn = "";
		DateFormat dateFormat = new SimpleDateFormat(FORMAT_MM_DDD_YYYY);
		try {
			dateToReturn = (dateValue != null) ? dateFormat.format(dateValue) : "";
		}catch(Exception exception) {
			dateToReturn = "";
		}
		return dateToReturn;
	}
	
	public static String getDateInDefaultFormat(java.sql.Date dateValue) {
		String dateToReturn = "";
		DateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		try {
			dateToReturn = (dateValue != null) ? dateFormat.format(dateValue) : "";
		}catch(Exception exception) {
			dateToReturn = "";
		}
		return dateToReturn;
	}
	
	public static String removeDollarAndComma(String value){
		try {
			if(value == null) return value;
			value = value.trim();
			value = value.replace(",", "");
			value = value.replace("$", "");
			Float floatValue = new Float(value);
			Integer decimalValue = floatValue.intValue();
			return decimalValue.toString();
			
		}catch(Exception exception){

	
		}	
		
		return value;
	}

}
