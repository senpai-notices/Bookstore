package au.edu.uts.aip.service.filter;

import au.edu.uts.aip.domain.validation.ValidationResult;
import javax.ejb.Singleton;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Singleton
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException exception) {
        ValidationResult result = new ValidationResult();
        result.getErrorMessages().add(exception.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).entity(result.toJson()).build();
    }
}
