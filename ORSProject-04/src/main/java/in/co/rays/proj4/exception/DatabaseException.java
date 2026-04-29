package in.co.rays.proj4.exception;

/**
 * DatabaseException represents a custom exception
 * used to handle database-related errors.
 * 
 * It is typically thrown when:
 * - Database connectivity issues occur
 * - SQL operations fail
 * - Data access layer encounters problems
 * 
 * This class extends Exception (checked exception),
 * so it must be handled or declared in method signatures.
 * 
 * Usage:
 * - Wrap SQL or persistence-related exceptions
 * - Provide meaningful database error messages
 * 
 * @author Nimish
 */
public class DatabaseException extends Exception{
    
    /**
     * Constructs a new DatabaseException with a message.
     * 
     * @param msg the detail message describing the exception
     */
    public DatabaseException(String msg) {
        super(msg);
    }

}