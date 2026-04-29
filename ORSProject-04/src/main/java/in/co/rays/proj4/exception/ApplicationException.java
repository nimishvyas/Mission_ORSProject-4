package in.co.rays.proj4.exception;

/**
 * ApplicationException represents a custom exception
 * used to handle application-level errors.
 * 
 * It is typically thrown when:
 * - Business logic fails
 * - System-level operations encounter issues
 * - Unexpected application behavior occurs
 * 
 * This class extends Exception (checked exception),
 * so it must be handled or declared in method signatures.
 * 
 * Usage:
 * - Wrap lower-level exceptions
 * - Provide meaningful error messages to higher layers
 * 
 * @author Nimish
 */
public class ApplicationException extends Exception{
    
    /**
     * Constructs a new ApplicationException with a message.
     * 
     * @param msg the detail message describing the exception
     */
    public ApplicationException(String msg) {
        super(msg);
    }

}