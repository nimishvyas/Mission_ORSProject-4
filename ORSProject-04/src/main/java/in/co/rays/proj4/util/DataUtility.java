package in.co.rays.proj4.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DataUtility is a utility class that provides helper methods for data type
 * conversion and formatting.
 * 
 * It provides methods to convert and format String, Integer, Long, Date and
 * Timestamp values. It also defines common date and time format constants used
 * throughout the application.
 * 
 * @author Nimish
 */
public class DataUtility {

	/** Date format used across the application, for example 2001-01-01. */
	public static final String APP_DATE_FORMAT = "dd-MM-yyyy";

	/**
	 * Date and time format used across the application, for example 01-01-2001
	 * 10:30:00.
	 */
	public static final String APP_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";

	/** SimpleDateFormat instance used for formatting and parsing date values. */
	public static final SimpleDateFormat formatter = new SimpleDateFormat(APP_DATE_FORMAT);

	/**
	 * SimpleDateFormat instance used for formatting and parsing date and time
	 * values.
	 */
	public static final SimpleDateFormat timeFormatter = new SimpleDateFormat(APP_TIME_FORMAT);

	/**
	 * Returns trimmed string value if it is not null, otherwise returns the
	 * original value.
	 * 
	 * @param val the string value to trim
	 * @return trimmed string or original value if null
	 */
	public static String getString(String val) {
		if (DataValidator.isNotNull(val)) {
			return val.trim();
		} else {
			return val;
		}
	}

	/**
	 * Converts any object to its String representation. Returns empty string if the
	 * object is null.
	 * 
	 * @param val the object to convert
	 * @return string representation of the object or empty string if null
	 */
	public static String getStringData(Object val) {
		if (val != null) {
			return val.toString();
		} else {
			return "";
		}
	}

	/**
	 * Converts a string value to an integer. Returns 0 if the value is not a valid
	 * integer.
	 * 
	 * @param val the string value to convert
	 * @return integer value or 0 if conversion fails
	 */
	public static Integer getInt(String val) {
		if (DataValidator.isInteger(val)) {
			return Integer.parseInt(val);
		} else {
			return null;
		}
	}
	public static Double getDouble(String val) {
		if (DataValidator.isDouble(val)) {
			return Double.parseDouble(val);
		} else {
			return null;
		}
	}


	/**
	 * Converts a string value to a long. Returns 0 if the value is not a valid
	 * long.
	 * 
	 * @param val the string value to convert
	 * @return long value or 0 if conversion fails
	 */
	public static long getLong(String val) {
		if (DataValidator.isLong(val)) {
			return Long.parseLong(val);
		} else {
			return 0;
		}
	}

	/**
	 * Converts a string value to a Date object using the application date format.
	 * Returns null if the value cannot be parsed.
	 * 
	 * @param val the date string to convert in yyyy-MM-dd format
	 * @return Date object or null if parsing fails
	 */
	public static Date getDate(String val) {
		Date date = null;
		try {
			date = formatter.parse(val);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * Converts a Date object to a formatted date string using the application date
	 * format. Returns empty string if formatting fails.
	 * 
	 * @param date the Date object to format
	 * @return formatted date string in yyyy-MM-dd format or empty string if fails
	 */
	public static String getDateString(Date date) {
		try {
			return formatter.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Converts a string value to a Timestamp object using the application time
	 * format. Returns null if the value cannot be parsed.
	 * 
	 * @param val the date time string to convert in dd-MM-yyyy HH:mm:ss format
	 * @return Timestamp object or null if parsing fails
	 */
	public static Timestamp getTimestamp(String val) {
		Timestamp timeStamp = null;
		try {
			timeStamp = new Timestamp((timeFormatter.parse(val)).getTime());
		} catch (Exception e) {
			return null;
		}
		return timeStamp;
	}

	/**
	 * Converts a long time value in milliseconds to a Timestamp object. Returns
	 * null if conversion fails.
	 * 
	 * @param time the time value in milliseconds
	 * @return Timestamp object or null if conversion fails
	 */
	public static Timestamp getTimestamp(long time) {
		Timestamp timeStamp = null;
		try {
			timeStamp = new Timestamp(time);
		} catch (Exception e) {
			return null;
		}
		return timeStamp;
	}

	/**
	 * Returns the current date and time as a Timestamp object. Returns null if an
	 * exception occurs.
	 * 
	 * @return current Timestamp or null if an exception occurs
	 */
	public static Timestamp getCurrentTimestamp() {
		Timestamp timeStamp = null;
		try {
			timeStamp = new Timestamp(new Date().getTime());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return timeStamp;
	}

	/**
	 * Converts a Timestamp object to its long time value in milliseconds. Returns 0
	 * if the Timestamp is null or conversion fails.
	 * 
	 * @param tm the Timestamp object to convert
	 * @return long time value in milliseconds or 0 if conversion fails
	 */
	public static long getTimestamp(Timestamp tm) {
		try {
			return tm.getTime();
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * Main method used to test the DataUtility methods.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		String s = "       Ram";
		System.out.println(s);
		System.out.println(DataUtility.getString(s));
		int i = 50;
		String s1 = "500";
		System.out.println(DataUtility.getStringData(i));
		System.out.println(DataUtility.getLong(s1));
		String s2 = "2001-01-01";
		Date dob = DataUtility.getDate(s2);
		System.out.println(dob);
		System.out.println(DataUtility.getDateString(dob));
	}

	}