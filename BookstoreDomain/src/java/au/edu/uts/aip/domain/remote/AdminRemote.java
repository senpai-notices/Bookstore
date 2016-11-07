package au.edu.uts.aip.domain.remote;

import javax.ejb.Remote;

/**
 * AdminBean is a JavaBean that is used to verify the user and admin the administrator account
 *
 * It has four methods. banAccount function is used to ban the administrator account unbanAccount
 * function is used to unban the administrator account approveVerificationRequest function is used
 * to approve the user's verification request rejectVerificationRequest function is used to reject
 * the user's verification request
 *
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@Remote
public interface AdminRemote {

    /**
     * rejectVerificationRequest function is used to reject the user's verification request return
     * none
     * @param username
     */
    void rejectVerificationRequest(String username);

    /**
     * approveVerificationRequest function is used to approve the user's verification request return
     * void
     * @param username
     */
    void approveVerificationRequest(String username);

    /**
     * This method is used to admin the administrator account banAccount function is used to ban the
     * administrator account
     * @param username
     */
    void banAccount(String username);

    /**
     * This method is used to admin the administrator account unbanAccount function is used to unban
     * the administrator account
     * @param username
     */
    void unbanAccount(String username);
}
