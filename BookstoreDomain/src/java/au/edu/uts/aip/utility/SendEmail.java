package au.edu.uts.aip.utility;

import au.edu.uts.aip.domain.UserBean;
import au.edu.uts.aip.entity.User;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author
 * https://www.mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/
 */
public class SendEmail {

    public static void SendActivationEmail(User user, String activateToken) throws MessagingException {
        final String username = "***REMOVED***";
        final String password = "***REMOVED***";

        String to = user.getEmail();
        //String from = "USNWGroup@aip.uts.edu.au";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "***REMOVED***");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            //message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Account activation");
            message.setText(activateToken);

            Transport.send(message);
        } catch (MessagingException ex) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
}
