package au.edu.uts.aip.service.resource;

import au.edu.uts.aip.domain.entity.User;
import au.edu.uts.aip.domain.utility.FileUtility;
import au.edu.uts.aip.domain.remote.UserRemote;
import java.io.File;
import java.io.FileInputStream;
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
    
    private static final Object syncRoot = new Object();
    
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
        // delete old files before uploading new file
        File pngFile = new File(uploadDirectory + File.separator + documentType + ".png");
        File jpegFile = new File(uploadDirectory + File.separator + documentType + ".jpeg");
        File pdfFile = new File(uploadDirectory + File.separator + documentType + ".pdf");
        pngFile.delete();
        jpegFile.delete();
        pdfFile.delete();
        
        String filePath = uploadDirectory + File.separator + documentType + "." + extension;
        try {
            InputStream decodedStream = Base64.getMimeDecoder().wrap(request.getInputStream());
            FileUtility.copy(decodedStream, filePath);
            synchronized(syncRoot){
                userBean.updateVerificationDocuments(username, documentType, filePath);
            }
        } catch (IOException ex) {
            Logger.getLogger(VerificationDocumentResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return Response.status(Response.Status.OK).build();
    }
    
    @GET
    @RolesAllowed({"ADMIN"})
    @Path("{username}/{documentType}")
    @Produces({"application/pdf", "image/png", "image/jpeg"})
    public Response get(@PathParam("username") String username,
                        @PathParam("documentType") String documentType){
        User user = userBean.getUser(username);
        File returnFile;
        switch (documentType) {
            case "id":
                returnFile = new File(user.getIdVerificationPath());
                break;
            case "residental":
                returnFile = new File(user.getResidentalVerificationPath());
                break;
            default:
                return Response.status(Response.Status.BAD_REQUEST).build();
        }
        
        String fileExtension = FileUtility.getExtension(returnFile.getPath());
        String type = null;
        if (fileExtension.endsWith("jpeg")){
            type = "image/jpeg";
        } else if (fileExtension.endsWith("png")){
            type = "image/png";
        } else if (fileExtension.endsWith("pdf")){
            type = "application/pdf";
        }
        
        return Response.ok((Object)returnFile, type)
                .header("Content-Disposition", "attachment; filename=\"" + returnFile.getName() + "\"").build();
    }
}
