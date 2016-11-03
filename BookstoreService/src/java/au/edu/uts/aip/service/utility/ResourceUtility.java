package au.edu.uts.aip.service.utility;

import au.edu.uts.aip.domain.validation.ValidationResult;
import javax.ws.rs.core.Response;

public class ResourceUtility {

    public static Response generate201Response(ValidationResult validationResult) {
        if (validationResult == null) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(422)
                    .entity(validationResult.toJson()).build();
        }
    }
    
        public static Response generate200Response(ValidationResult validationResult) {
        if (validationResult == null) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(422)
                    .entity(validationResult.toJson()).build();
        }
    }
}
