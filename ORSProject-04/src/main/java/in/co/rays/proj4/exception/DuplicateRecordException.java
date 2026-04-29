package in.co.rays.proj4.exception;

/**
 * DuplicateRecordException represents a custom exception
 * used to handle scenarios where a duplicate record is encountered.
 * 
 * It is typically thrown when:
 * - Attempting to insert a record that already exists
 * - Unique constraints (like login, email, etc.) are violated
 * 
 * This class extends Exception (checked exception),
 * so it must be handled or declared in method signatures.
 * 
 * Usage:
 * - Prevent duplicate entries in database
 * - Provide meaningful feedback to users or higher layers
 * 
 * @author Nimish
 */
public class DuplicateRecordException extends Exception{

    /**
     * Constructs a new DuplicateRecordException with a message.
     * 
     * @param msg the detail message describing the exception
     */
    public DuplicateRecordException (String msg) {
        super(msg);
    }
}