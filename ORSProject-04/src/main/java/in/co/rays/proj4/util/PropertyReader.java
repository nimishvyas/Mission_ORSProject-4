package in.co.rays.proj4.util;

import java.util.ResourceBundle;

/**
 * PropertyReader is a utility class used to read values from a resource bundle.
 * 
 * It supports:
 * - Fetching values using a key
 * - Replacing placeholders in messages with dynamic values
 * 
 * Placeholders format:
 * - {0}, {1}, {2}, ... for parameter substitution
 * 
 * This class is commonly used for:
 * - Error messages
 * - Labels and constants
 * - Internationalization (i18n) support
 * 
 * If a key is not found, the key itself is returned as a fallback.
 * 
 * Resource bundle used:
 * - in.co.rays.proj4.bundle.system
 * 
 * @author Nimish
 */
public class PropertyReader {

    /** Resource bundle for reading application properties */
    private static ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.proj4.bundle.system");

    /**
     * Retrieves value from resource bundle using the given key.
     * 
     * If key is not found, returns the key itself.
     * 
     * @param key property key
     * @return value corresponding to the key or key itself if not found
     */
    public static String getValue(String key) {

        String val = null;

        try {
            val = rb.getString(key); // {0} is required
        } catch (Exception e) {
            val = key;
        }
        return val;
    }

    /**
     * Retrieves value and replaces a single placeholder {0} with the given parameter.
     * 
     * Example:
     * - key: "error.require" → "{0} is required"
     * - param: "LoginId"
     * - result: "LoginId is required"
     * 
     * @param key   property key
     * @param param value to replace {0}
     * @return formatted message
     */
    public static String getValue(String key, String param) {
        String msg = getValue(key); // {0} is required
        msg = msg.replace("{0}", param);
        return msg;
    }

    /**
     * Retrieves value and replaces multiple placeholders ({0}, {1}, ...) with given parameters.
     * 
     * Example:
     * - key: "error.multipleFields" → "{0} and {1} are required"
     * - params: ["Roll No", "Name"]
     * - result: "Roll No and Name are required"
     * 
     * @param key    property key
     * @param params array of values to replace placeholders
     * @return formatted message
     */
    public static String getValue(String key, String[] params) {
        String msg = getValue(key); // {0} and {1} are required.
        for (int i = 0; i < params.length; i++) {
            msg = msg.replace("{" + i + "}", params[i]);
        }
        return msg;
    }

    /**
     * Main method to demonstrate usage of PropertyReader.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {

        System.out.println("Single key example:");
        System.out.println(PropertyReader.getValue("error.require"));

        System.out.println("\nSingle parameter replacement example:");
        System.out.println(PropertyReader.getValue("error.require", "loginId"));

        System.out.println("\nMultiple parameter replacement example:");
        String[] params = { "Roll No", "Student Name" };
        System.out.println(PropertyReader.getValue("error.multipleFields", params));
    }
}