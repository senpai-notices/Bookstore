package au.edu.uts.aip.domain.remote;

import javax.ejb.Remote;

/**
 *
 * @author x
 */
@Remote
public interface EmailRemote {

    public enum Protocol {
        SMTP, SMTPS, TLS
    }

    final int port = 587;
    final String host = "***REMOVED***";
    final String from = "***REMOVED***";
    final boolean auth = true;
    final String username = "***REMOVED***";
    final String password = "***REMOVED***";
    final Protocol protocol = Protocol.TLS;
    final boolean debug = true;

    /**
     * Send an email with a subject, body and destination email address.
     * @param to
     * @param subject
     * @param body
     */
    void sendEmail(String to, String subject, String body);

}
