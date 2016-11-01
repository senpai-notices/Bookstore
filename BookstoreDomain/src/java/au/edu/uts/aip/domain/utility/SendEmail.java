package au.edu.uts.aip.domain.utility;

import au.edu.uts.aip.domain.ejb.UserBean;
import au.edu.uts.aip.domain.entity.User;
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
 * @author https://www.mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/
 */
public class SendEmail {

    private static Session emailSession = null;
    
    private static Session getEmailSession(){
        if (emailSession != null){
            return emailSession;
        }
        
        final String username = "***REMOVED***";
        final String password = "***REMOVED***";
        //String from = "USNWGroup@aip.uts.edu.au";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "***REMOVED***");
        properties.put("mail.smtp.port", "587");

        emailSession = Session.getInstance(properties,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        
        return emailSession;
    }
    
    public static void SendEmail(String to, String subject, String body) throws MessagingException {
        Session session = getEmailSession();
        
        try{
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
        } catch (MessagingException ex){
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
}
