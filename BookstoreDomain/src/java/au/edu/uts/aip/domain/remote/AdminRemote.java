package au.edu.uts.aip.domain.remote;

import javax.ejb.Remote;

@Remote
public interface AdminRemote {

    void rejectVerificationRequest(String username);

    void approveVerificationRequest(String username);
}
