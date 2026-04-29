package in.co.rays.proj4.exception;

/**
 * RecordNotFoundException represents a custom exception
 * used when a requested record is not found.
 * 
 * It is typically thrown when:
 * - Searching for a record that does not exist
 * - Fetch operations return no results
 * - Invalid identifiers are used to retrieve data
 * 
 * This class extends Exception (checked exception),
 * so it must be handled or declared in method signatures.
 * 
 * Usage:
 * - Indicate absence of expected data
 * - Provide meaningful feedback to users or upper layers
 * 
 * @author Nimish
 */
public class RecordNotFoundException extends Exception{

    /**
     * Constructs a new RecordNotFoundException with a message.
     * 
     * @param msg the detail message describing the exception
     */
    public RecordNotFoundException(String msg) {
        super(msg);
    }
}