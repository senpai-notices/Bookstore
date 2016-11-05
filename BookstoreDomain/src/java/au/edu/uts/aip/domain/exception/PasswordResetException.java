package au.edu.uts.aip.domain.exception;

public class PasswordResetException extends Exception {
    public PasswordResetException(){
        
    }
    
    public PasswordResetException(String message){
        super(message);
    }
}
