package au.edu.uts.aip.service.filter;

import au.edu.uts.aip.domain.validation.ValidationResult;
import java.util.ArrayList;
import java.util.Set;
import javax.annotation.Priority;
import javax.validation.ConstraintViolation;
import javax.ws.rs.Priorities;
import javax.ws.rs.ext.Provider;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

@Provider
@Priority(Priorities.ENTITY_CODER)
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<?>> constraints = exception.getConstraintViolations();
        ArrayList<String> paths = new ArrayList<>();
        for (ConstraintViolation constraint : constraints) {
            constraint.getPropertyPath().forEach(path -> {
                paths.add(0, path.getName());
            });
            result.addFormError(paths.get(0), constraint.getMessage());
        }
        
        return Response.status(Response.Status.BAD_REQUEST).entity(result.toJson()).build();
    }
    
}
