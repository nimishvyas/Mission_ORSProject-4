package in.co.rays.proj4.util;

import java.util.Calendar;
import java.util.Date;

/**
 * DataValidator is a utility class that provides various validation methods
 * for different types of input data.
 * 
 * It includes validation for:
 * - Null and non-null values
 * - Numeric values (Integer, Long)
 * - Email format
 * - Name format
 * - Roll number format
 * - Password strength and length
 * - Phone number format and length
 * - Date validation and day checks
 * 
 * This class is widely used for input validation before processing or storing data.
 * 
 * @author Nimish
 */
public class DataValidator {

    /**
     * Checks whether the given string is null or empty after trimming.
     * 
     * @param val the string to check
     * @return true if value is null or empty, otherwise false
     */
    public static boolean isNull(String val) {
        if (val == null || val.trim().length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks whether the given string is not null and not empty.
     * 
     * @param val the string to check
     * @return true if value is not null and not empty, otherwise false
     */
    public static boolean isNotNull(String val) {
        return !isNull(val);
    }

    /**
     * Validates whether the given string is a valid integer.
     * 
     * @param val the string to validate
     * @return true if valid integer, otherwise false
     */
    public static boolean isInteger(String val) {
        if (isNotNull(val)) {
            try {
                Integer.parseInt(val);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Validates whether the given string is a valid long value.
     * 
     * @param val the string to validate
     * @return true if valid long, otherwise false
     */
    public static boolean isLong(String val) {
        if (isNotNull(val)) {
            try {
                Long.parseLong(val);
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Validates whether the given string is in a proper email format.
     * 
     * @param val the email string to validate
     * @return true if valid email, otherwise false
     */
    public static boolean isEmail(String val) {

        String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        if (isNotNull(val)) {
            try {
                return val.matches(emailreg);
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Validates whether the given string is a valid name.
     * 
     * Name should not start with space or hyphen and may contain letters,
     * spaces, dots, apostrophes, or hyphens.
     * 
     * @param val the name string to validate
     * @return true if valid name, otherwise false
     */
    public static boolean isName(String val) {

        String namereg = "^[^-\\s][\\p{L} .'-]+$";

        if (isNotNull(val)) {
            try {
                return val.matches(namereg);
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Validates whether the given string is a valid roll number.
     * 
     * Expected format: 2 letters followed by 3 digits (e.g., AB123)
     * 
     * @param val the roll number string
     * @return true if valid roll number, otherwise false
     */
    public static boolean isRollNo(String val) {

        String rollreg = "[a-zA-Z]{2}[0-9]{3}";

        if (isNotNull(val)) {
            try {
                return val.matches(rollreg);
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Validates whether the given string is a strong password.
     * 
     * Password must:
     * - Contain at least one digit
     * - Contain one lowercase and one uppercase letter
     * - Contain one special character (@#$%^&+=)
     * - Have no whitespace
     * - Be between 8 to 12 characters long
     * 
     * @param val the password string
     * @return true if valid password, otherwise false
     */
    public static boolean isPassword(String val) {

        String passreg = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,12}";

        if (isNotNull(val)) {
            try {
                return val.matches(passreg);
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Validates whether the password length is between 8 and 12 characters.
     * 
     * @param val the password string
     * @return true if length is valid, otherwise false
     */
    public static boolean isPasswordLength(String val) {

        if (isNotNull(val) && val.length() >= 8 && val.length() <= 12) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Validates whether the given string is a valid Indian phone number.
     * 
     * Must start with digits 6-9 and be exactly 10 digits long.
     * 
     * @param val the phone number string
     * @return true if valid phone number, otherwise false
     */
    public static boolean isPhoneNo(String val) {

        String phonereg = "^[6-9][0-9]{9}$";

        if (isNotNull(val)) {
            try {
                return val.matches(phonereg);
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Validates whether the phone number length is exactly 10 digits.
     * 
     * @param val the phone number string
     * @return true if length is 10, otherwise false
     */
    public static boolean isPhoneLength(String val) {

        if (isNotNull(val) && val.length() == 10) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Validates whether the given string is a valid date.
     * 
     * Uses DataUtility to parse the date.
     * 
     * @param val the date string
     * @return true if valid date, otherwise false
     */
    public static boolean isDate(String val) {

        Date d = null;

        if (isNotNull(val)) {
            d = DataUtility.getDate(val);
        }
        return d != null;
    }

    /**
     * Checks whether the given date falls on a Sunday.
     * 
     * @param val the date string
     * @return true if the date is Sunday, otherwise false
     */
    public static boolean isSunday(String val) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DataUtility.getDate(val));
        int i = cal.get(Calendar.DAY_OF_WEEK);

        if (i == Calendar.SUNDAY) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Main method used for testing validation methods.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {

        String s = " ";
        System.out.println(DataValidator.isNull(s));
        System.out.println(DataValidator.isNotNull("Ram"));
        System.out.println(DataValidator.isInteger("Hek"));
        System.out.println(DataValidator.isLong("55"));
        System.out.println(DataValidator.isEmail("ramji.satyug@ramrajya.com"));
        System.out.println(DataValidator.isName("Hello"));
        System.out.println(DataValidator.isPassword("Rahul@123"));
        System.out.println(isPhoneNo("8989898989"));
        System.out.println(DataValidator.isDate("2001-03-02"));
        System.out.println(DataValidator.isSunday("2026-03-22"));
    }
}