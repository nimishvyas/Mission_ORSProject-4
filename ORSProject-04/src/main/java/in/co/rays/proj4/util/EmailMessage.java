package in.co.rays.proj4.util;

/**
 * EmailMessage is a simple data holder class that represents an email.
 * 
 * It contains details required to send an email such as:
 * - Recipient address (to)
 * - Subject of the email
 * - Message content
 * - Message type (HTML or Text)
 * 
 * This class is typically used by email services to transfer email data
 * between different layers of the application.
 * 
 * Message types:
 * - HTML_MSG (1): Email content is in HTML format
 * - TEXT_MSG (2): Email content is plain text
 * 
 * Default message type is TEXT_MSG.
 * 
 * @author Nimish
 */
public class EmailMessage {

    /** Email recipient address */
    private String to;

    /** Subject of the email */
    private String subject;

    /** Body/content of the email */
    private String message;

    /** Type of message (HTML_MSG or TEXT_MSG). Default is TEXT_MSG */
    private int messageType = TEXT_MSG;

    /** Constant representing HTML message type */
    public static final int HTML_MSG = 1;

    /** Constant representing plain text message type */
    public static final int TEXT_MSG = 2;

    /**
     * Default constructor.
     */
    public EmailMessage() {
    }

    /**
     * Parameterized constructor to initialize email details.
     * 
     * @param to recipient email address
     * @param subject subject of the email
     * @param message email content/body
     */
    public EmailMessage(String to, String subject, String message) {
        this.to = to;
        this.subject = subject;
        this.message = message;
    }

    /**
     * Sets the recipient email address.
     * 
     * @param to recipient email address
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * Returns the recipient email address.
     * 
     * @return recipient email address
     */
    public String getTo() {
        return to;
    }

    /**
     * Sets the subject of the email.
     * 
     * @param subject email subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Returns the subject of the email.
     * 
     * @return email subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the email message content.
     * 
     * @param message email body/content
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns the email message content.
     * 
     * @return email body/content
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the type of the email message.
     * 
     * @param messageType type of message (HTML_MSG or TEXT_MSG)
     */
    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    /**
     * Returns the type of the email message.
     * 
     * @return message type (HTML_MSG or TEXT_MSG)
     */
    public int getMessageType() {
        return messageType;
    }

}