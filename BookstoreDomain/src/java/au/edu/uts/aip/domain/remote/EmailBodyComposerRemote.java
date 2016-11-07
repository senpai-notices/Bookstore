package au.edu.uts.aip.domain.remote;

import javax.ejb.Remote;

/**
 * Composes email bodies, including salutations, signatures and main content
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@Remote
public interface EmailBodyComposerRemote {

    static final String SALUTATION_PREFIX = "Dear";
    static final String SIGNATURE_PREFIX = "Regards";
    static final String SENDER_NAME = "The Bookstore Team";
    static final String BRAND_NAME = "Bookstore";
    static final String BODY_ACCOUNT_ACTIVATION
            = "Welcome to " + BRAND_NAME + "!\n\nTo get started, please activate your account by "
            + "clicking the link below:\n%1$s";
    static final String BODY_RESET_PASSWORD
            = "Please click on the link below to reset your password\n%1$s";
    static final String BODY_VERIFICATION_REJECT
            = "Thank you for submitting your documents for identity verification. "
            + "However, we are unable to approve your submission due to the following reason:\n\n"
            + "\t%1$s";
    static final String BODY_VERIFICATION_APPROVE
            = "Thank you for submitting your documents for identity verification. "
            + "Your account is now verified.";
    static final String BODY_ACCOUNT_BAN
            = "Your account has been banned.";
    static final String BODY_ACCOUNT_UNBAN
            = "Your account has been unbanned.";
    static final String BODY_ORDER_FAIL
            = "Your payment has failed to process for your order %1$s. Please try again.";
    static final String BODY_ORDER_PENDING
            = "I don't know a use case for this. %1$s";
    static final String BODY_ORDER_COMPLETE_BUYER
            = "Thank you for your order from " + BRAND_NAME + ". Below is your order information:"
            + "\n\n%1$s";
    static final String BODY_ORDER_COMPLETE_SELLER
            = "You've just made a sale on " + BRAND_NAME + "! Please keep in mind that bank "
            + "transfers can take up to seven days to appear in your bank statements.\n\n"
            + "Below is your order information:\n\n%1$s";
    
    /**
     * Main formatter that combines name, info, opening and closing.
     */
    String format(String name, String info);
    
    /**
     * Generate email body for account activation
     */
    String onAccountActivation(String name, String activationUrl);

    /**
     * Generate email body for account ban
     */
    String onAccountBan(String name);

    /**
     * Generate email body for account unban
     */
    String onAccountUnban(String name);

    /**
     * Generate email body for order complete for the buyer
     */
    String onOrderCompleteBuyer(String name, String orderInfo);

    /**
     * Generate email body for order complete for the seller
     */
    String onOrderCompleteSeller(String name, String orderInfo);

    /**
     * Generate email body for when order fails
     */
    String onOrderFail(String name, String orderInfo);

    /**
     * Generate email body for a pending order
     */
    String onOrderPending(String name, String orderInfo);

    /**
     * Generate email body for password resets
     */
    String onPasswordReset(String name, String resetUrl);

    /**
     * Generate email body for approval of verification
     */
     String onVerificationApprove(String name);
     
     /**
     * Generate email body for rejection of verfication
     */
     String onVerificationReject(String name, String reason);

}
