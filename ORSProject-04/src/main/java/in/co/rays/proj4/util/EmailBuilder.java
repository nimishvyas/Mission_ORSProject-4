package in.co.rays.proj4.util;

import java.util.HashMap;

/**
 * EmailBuilder is a utility class responsible for generating HTML-based email
 * messages for different user-related actions.
 * 
 * It provides methods to build:
 * - User registration email
 * - Forgot password email
 * - Change password confirmation email
 * 
 * Each method accepts a map containing user details and dynamically inserts
 * those values into a predefined HTML template.
 * 
 * This class is typically used in email services to send formatted emails
 * to users.
 * 
 * @author Nimish
 */
public class EmailBuilder {

    /**
     * Generates a user registration success email message.
     * 
     * The email includes:
     * - Welcome message
     * - Login ID
     * - Password
     * - Security instruction to change password
     * - Contact information for support
     * 
     * Expected keys in map:
     * - login
     * - password
     * 
     * @param map HashMap containing user registration details
     * @return HTML formatted email message as String
     */
    public static String getUserRegistrationMessage(HashMap<String, String> map) {
        StringBuilder msg = new StringBuilder();
        msg.append("<HTML><BODY>");
        msg.append("<H1>Welcome to ORS, ").append(map.get("login")).append("!</H1>");
        msg.append("<P>Your registration is successful. You can now log in and manage your account.</P>");
        msg.append("<P><B>Login Id: ").append(map.get("login")).append("<BR>Password: ").append(map.get("password"))
                .append("</B></P>");
        msg.append("<P>Change your password after logging in for security reasons.</P>");
        msg.append("<P>For support, contact +91 98273 60504 or hrd@sunrays.co.in.</P>");
        msg.append("</BODY></HTML>");
        return msg.toString();
    }

    /**
     * Generates a forgot password email message.
     * 
     * The email includes:
     * - User's full name
     * - Login ID
     * - Password
     * 
     * Expected keys in map:
     * - firstName
     * - lastName
     * - login
     * - password
     * 
     * @param map HashMap containing user details
     * @return HTML formatted email message as String
     */
    public static String getForgetPasswordMessage(HashMap<String, String> map) {
        StringBuilder msg = new StringBuilder();
        msg.append("<HTML><BODY>");
        msg.append("<H1>Password Recovery</H1>");
        msg.append("<P>Hello, ").append(map.get("firstName")).append(" ").append(map.get("lastName")).append(".</P>");
        msg.append("<P>Your login details are:</P>");
        msg.append("<P><B>Login Id: ").append(map.get("login")).append("<BR>Password: ").append(map.get("password"))
                .append("</B></P>");
        msg.append("</BODY></HTML>");
        return msg.toString();
    }

    /**
     * Generates a change password confirmation email message.
     * 
     * The email includes:
     * - Confirmation message
     * - User's full name
     * - Login ID
     * - Updated password
     * 
     * Expected keys in map:
     * - firstName
     * - lastName
     * - login
     * - password
     * 
     * @param map HashMap containing updated user details
     * @return HTML formatted email message as String
     */
    public static String getChangePasswordMessage(HashMap<String, String> map) {
        StringBuilder msg = new StringBuilder();
        msg.append("<HTML><BODY>");
        msg.append("<H1>Password Changed Successfully</H1>");
        msg.append("<P>Dear ").append(map.get("firstName")).append(" ").append(map.get("lastName"))
                .append(", your password has been updated.</P>");
        msg.append("<P>Your updated login details are:</P>");
        msg.append("<P><B>Login Id: ").append(map.get("login")).append("<BR>New Password: ").append(map.get("password"))
                .append("</B></P>");
        msg.append("</BODY></HTML>");
        return msg.toString();
    }

}