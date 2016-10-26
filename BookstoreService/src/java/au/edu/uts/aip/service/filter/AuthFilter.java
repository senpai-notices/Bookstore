package au.edu.uts.aip.service.filter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

/**
 * Authentication filter for resource methods that have RoleAllowed,
 * PermitAll, or DenyAll annotation
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;
    
    @Context
    private SecurityContext securityContext;
    
    @Context
    private HttpServletRequest request;

    @Context
    private HttpServletResponse response;

    /**
     * Check resource method annotation and user privilege
     * Prevent the resource method from being invoked if user do not have enough
     * privilege to access the resource method
     * @param requestContext use to determine whether the request can reach the resource or not
     * @throws IOException 
     */
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        Method resourceMethod = resourceInfo.getResourceMethod();
        
        // allow resource method to be invoked if it is decorated with PermitAll annotation
        PermitAll permitAll = resourceMethod.getAnnotation(PermitAll.class);
        if (permitAll != null){
            return;
        }
        
        // reject all request if resource method is decorated with DenyAll annotation
        DenyAll denyAll = resourceMethod.getAnnotation(DenyAll.class);
        if (denyAll != null){
            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
            return;
        }
        
        // allow resource method to be invoked it does not have any RolesAllowed filter
        RolesAllowed rolesAllowed = resourceMethod.getAnnotation(RolesAllowed.class);
        if (rolesAllowed == null){
            return;
        }
        
        // otherwise, check user privilege
        try {
            boolean authenticated = request.authenticate(response);
            if (!authenticated){
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                return;
            }
        } catch (ServletException ex) {
            Logger.getLogger(AuthFilter.class.getName()).log(Level.SEVERE, null, ex);
            requestContext.abortWith(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
            return;
        }
        if (securityContext.getUserPrincipal() == null){
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
        
        String[] roles = rolesAllowed.value();
        for(String role: roles){
            if (securityContext.isUserInRole(role)){
                return;
            }
        }
        
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }
    
    
    
}
