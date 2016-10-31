package au.edu.uts.aip.service.resource;

import au.edu.uts.aip.domain.utility.FileUtility;
import au.edu.uts.aip.domain.remote.UserRemote;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/document")
public class VerificationDocumentResource {
    
    @Context
    private HttpServletRequest request;
    
    @Context
    private ServletContext servletContext;
    
    @EJB
    private UserRemote userBean;
    
    @POST
    @RolesAllowed({"USER"})
    @Path("{documentType}")
    @Consumes({"application/pdf", "image/jpeg", "image/png"})
    public Response post(@PathParam("documentType") String documentType){
        if (!documentType.equals("id") && !documentType.equals("residental")){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        
        String extension = request.getHeader("Content-Type").split("/")[1];
        String username = request.getUserPrincipal().getName();
        String baseUploadFoler = servletContext.getRealPath("/../../../../");
        
        String userFolder = new File(baseUploadFoler).getPath() + File.separator + username;
        File uploadDirectory = new File(userFolder);
        if (!uploadDirectory.exists()){
            uploadDirectory.mkdir();
        }
        File pngFile = new File(uploadDirectory + File.separator + documentType + ".pgn");
        File jpegFile = new File(uploadDirectory + File.separator + documentType + ".jpeg");
        File pdfFile = new File(uploadDirectory + File.separator + documentType + ".pdf");
        pngFile.delete();
        jpegFile.delete();
        pdfFile.delete();
        
        String filePath = uploadDirectory + File.separator + documentType + "." + extension;
        try {
            InputStream decodedStream = Base64.getMimeDecoder().wrap(request.getInputStream());
            FileUtility.copy(decodedStream, filePath);
        } catch (IOException ex) {
            Logger.getLogger(VerificationDocumentResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return Response.status(Response.Status.OK).build();
    }
    
    @GET
    @RolesAllowed({"ADMIN"})
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response get(@QueryParam("username") String username,
                        @QueryParam("documentType") String documentType){
        
        return Response.status(Response.Status.OK).build();
    }
}
