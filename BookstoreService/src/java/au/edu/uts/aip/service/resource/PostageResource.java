package au.edu.uts.aip.service.resource;

import au.edu.uts.aip.domain.auspost.dto.AuspostPostageGet;
import au.edu.uts.aip.domain.ejb.PostageBean;

import javax.ejb.EJB;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * May not be required
 */
@Path("/postage")
public class PostageResource {

    @EJB
    private PostageBean postageBean;

    @Deprecated
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("calculate")
    public Response calculate(AuspostPostageGet auspostPostageGet) {
        JsonObject response = postageBean.calculatePostageCostJson(auspostPostageGet);
        return Response.ok(response, MediaType.APPLICATION_JSON).build();
    }

    @Deprecated
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("calculate_test")
    public Response calculateTest() {
        postageBean.calculatePostageCost(5, "2000", "3000", "AUS_PARCEL_REGULAR");
        return Response.ok().build();
    }

}
