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
    @Path("calculate_reg_test")
    public Response calculateRegularTest() {
        postageBean.calculatePostageCost(32, 800, 9999, "AUS_PARCEL_REGULAR");
        return Response.ok().build();
    }

    @Deprecated
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("calculate_exp_test")
    public Response calculateExpressTest() {
        postageBean.calculatePostageCost(32, 800, 9999, "AUS_PARCEL_EXPRESS");
        return Response.ok().build();
    }

}
