package in.co.rays.proj4.util;

import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import in.co.rays.proj4.exception.ApplicationException;

/**
 * EmailUtility is a helper class responsible for sending emails using SMTP.
 * 
 * It reads email configuration such as SMTP server, port, login credentials
 * from a resource bundle and uses JavaMail API to send emails.
 * 
 * This class supports:
 * - Sending plain text emails
 * - Sending HTML emails
 * - Multiple recipients (comma-separated)
 * 
 * It uses EmailMessage DTO to receive email details.
 * 
 * Configuration keys used:
 * - smtp.server
 * - smtp.port
 * - email.login
 * - email.pwd
 * 
 * Any failure during email sending is wrapped into ApplicationException.
 * 
 * @author Nimish
 */
public class EmailUtility {

    /** Resource bundle for reading email configuration properties */
    static ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.proj4.bundle.system");

    /** SMTP server host name */
    private static final String SMTP_HOST_NAME = rb.getString("smtp.server");

    /** SMTP server port */
    private static final String SMTP_PORT = rb.getString("smtp.port");

    /** Sender email address */
    private static final String emailFromAddress = rb.getString("email.login");

    /** Sender email password */
    private static final String emailPassword = rb.getString("email.pwd");

    /** Properties object used to configure mail session */
    private static Properties props = new Properties();

    /**
     * Static block to initialize SMTP properties.
     */
    static {
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.debug", "true");
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.socketFactory.port", SMTP_PORT);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
    }

    /**
     * Sends an email using the provided EmailMessage object.
     * 
     * This method:
     * - Creates a mail session with authentication
     * - Builds the email message (from, to, subject, content)
     * - Determines content type (HTML or plain text)
     * - Sends the email using SMTP transport
     * 
     * @param emailMessageDTO EmailMessage object containing email details
     * @throws ApplicationException if any error occurs while sending email
     */
    public static void sendMail(EmailMessage emailMessageDTO) throws ApplicationException {
        try {
            // Setup mail session
            Session session = Session.getDefaultInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(emailFromAddress, emailPassword);
                }
            });

            // Create and setup the email message
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(emailFromAddress));
            msg.setRecipients(Message.RecipientType.TO, getInternetAddresses(emailMessageDTO.getTo()));
            msg.setSubject(emailMessageDTO.getSubject());

            // Set content type based on message type
            String contentType = emailMessageDTO.getMessageType() == EmailMessage.HTML_MSG ? "text/html" : "text/plain";
            msg.setContent(emailMessageDTO.getMessage(), contentType);

            // Send the email
            Transport.send(msg);

        } catch (Exception ex) {
            throw new ApplicationException("Email Error: " + ex.getMessage());
        }
    }

    /**
     * Converts a comma-separated string of email addresses into an array of InternetAddress.
     * 
     * Example input: "abc@gmail.com, xyz@yahoo.com"
     * 
     * @param emails comma-separated email string
     * @return array of InternetAddress objects
     * @throws Exception if email parsing fails
     */
    private static InternetAddress[] getInternetAddresses(String emails) throws Exception {
        if (emails == null || emails.isEmpty()) {
            return new InternetAddress[0];
        }
        String[] emailArray = emails.split(",");
        InternetAddress[] addresses = new InternetAddress[emailArray.length];
        for (int i = 0; i < emailArray.length; i++) {
            addresses[i] = new InternetAddress(emailArray[i].trim());
        }
        return addresses;
    }

}