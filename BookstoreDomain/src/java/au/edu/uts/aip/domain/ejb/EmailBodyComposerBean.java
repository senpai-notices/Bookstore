package au.edu.uts.aip.domain.ejb;

import au.edu.uts.aip.domain.remote.EmailBodyComposerRemote;
import javax.ejb.Stateless;

/**
 * @inheritDoc
 */
@Stateless
public class EmailBodyComposerBean implements EmailBodyComposerRemote {

    /**
     * @inheritDoc
     */
    @Override
    public String onAccountActivation(String name, String activationUrl) {
        return format(name, String.format(BODY_ACCOUNT_ACTIVATION, activationUrl));
    }

    /**
     * @inheritDoc
     */
    @Override
    public String onPasswordReset(String name, String resetUrl) {
        return format(name, String.format(BODY_RESET_PASSWORD, resetUrl));
    }

    /**
     * @inheritDoc
     */
    @Override
    public String onVerificationReject(String name, String reason) {
        return format(name, String.format(BODY_VERIFICATION_REJECT, reason));
    }

    /**
     * @inheritDoc
     */
    @Override
    public String onVerificationApprove(String name) {
        return format(name, BODY_VERIFICATION_APPROVE);
    }

    /**
     * @inheritDoc
     */
    @Override
    public String onAccountBan(String name) {
        return format(name, BODY_ACCOUNT_BAN);
    }

    /**
     * @inheritDoc
     */
    @Override
    public String onAccountUnban(String name) {
        return format(name, BODY_ACCOUNT_UNBAN);
    }

    /**
     * @inheritDoc
     */
    @Override
    public String onOrderFail(String name, String orderInfo) {
        return format(name, format(BODY_ORDER_FAIL, orderInfo));
    }

    /**
     * @inheritDoc
     */
    @Override
    public String onOrderPending(String name, String orderInfo) {
        return format(name, format(BODY_ORDER_PENDING, orderInfo));
    }

    /**
     * @inheritDoc
     */
    @Override
    public String onOrderCompleteBuyer(String name, String orderInfo) {
        return format(name, format(BODY_ORDER_COMPLETE_BUYER, orderInfo));
    }

    /**
     * @inheritDoc
     */
    @Override
    public String onOrderCompleteSeller(String name, String orderInfo) {
        return format(name, format(BODY_ORDER_COMPLETE_SELLER, orderInfo));
    }

    /**
     * @inheritDoc
     */
    @Override
    public String format(String name, String info) {
        return String.format(
                "%1$s %2$s,\n\n%3$s\n\n%4$s,\n%5$s",
                SALUTATION_PREFIX, name, info, SIGNATURE_PREFIX, SENDER_NAME
        );
    }
}
