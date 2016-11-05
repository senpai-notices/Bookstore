package au.edu.uts.aip.domain.exception;

public class TokenGenerationException extends Exception {
    public TokenGenerationException(){
        
    }
    
    public TokenGenerationException(String message){
        super(message);
    }
}
