package au.edu.uts.aip.filter;

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

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        Method resourceMethod = resourceInfo.getResourceMethod();
        
        PermitAll permitAll = resourceMethod.getAnnotation(PermitAll.class);
        if (permitAll != null){
            return;
        }
        
        DenyAll denyAll = resourceMethod.getAnnotation(DenyAll.class);
        if (denyAll != null){
            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
            return;
        }
        
        RolesAllowed rolesAllowed = resourceMethod.getAnnotation(RolesAllowed.class);
        if (rolesAllowed == null){
            return;
        }
        
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
