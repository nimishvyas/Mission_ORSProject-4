package in.co.rays.proj4.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtility {

	public static final String APP_DATE_FORMAT = "yyyy-MM-dd";
	
	public static final String APP_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";
	
	public static final SimpleDateFormat formatter = new SimpleDateFormat(APP_DATE_FORMAT);
	
	public static final SimpleDateFormat timeFormatter = new SimpleDateFormat(APP_TIME_FORMAT);
	
	public static String getString(String val) {
		if (DataValidator.isNotNull(val)) {
			return val.trim();
		} else {
			return val;
		}
	}
	
	public static String getStringData(Object val) {
		if (val != null) {
			return val.toString();
		} else {
			return "";
		}
	}
	
	public static int getInt(String val) {
		if (DataValidator.isInteger(val)) {
			return Integer.parseInt(val);
		} else {
			return 0;
		}
	}
	
	public static long getLong(String val) {
		if(DataValidator.isLong(val)) {
			return Long.parseLong(val);
		} else {
			return 0;
		}
	}
	
	public static Date getDate(String val) {
		Date date = null;
		try {
			date = formatter.parse(val);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static String getDateString (Date date) {
		try {
			return formatter.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static Timestamp getTimestamp(String val) {
		Timestamp timeStamp = null;
		try {
			timeStamp = new Timestamp((timeFormatter.parse(val)).getTime());
		} catch (Exception e) {
			return null;
		}
		return timeStamp;
	}
	
	public static Timestamp getTimestamp(long time) {
		Timestamp timeStamp = null;
		try {
			timeStamp = new Timestamp(time);
		} catch (Exception e) {
			return null;
		}
		return timeStamp;
	}
	
	public static Timestamp getCurrentTimestamp() {
		Timestamp timeStamp = null;
		try {
			timeStamp = new Timestamp(new Date().getTime());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return timeStamp;
	}
	
	public static long getTimestamp(Timestamp tm) {
		try {
			return tm.getTime();
		} catch (Exception e) {
			return 0;
		}
	}
	public static void main(String[] args) {
		
		String s = "       Ram";
		System.out.println(s);
		System.out.println(DataUtility.getString(s));
		int i = 50;
		String s1= "500";
		System.out.println(DataUtility.getStringData(i));
		System.out.println(DataUtility.getLong(s1));
		String s2 = "2001-01-01";
		Date dob = DataUtility.getDate(s2);
		System.out.println(dob);
		System.out.println(DataUtility.getDateString(dob));
		
	}
}
