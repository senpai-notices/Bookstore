package au.edu.uts.aip.service.resource;

import au.edu.uts.aip.domain.auspost.dto.AuspostPostageFeeGet;
import au.edu.uts.aip.domain.response.SerialResponse;
import au.edu.uts.aip.domain.ejb.PostalBean;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * May not be required
 */
@Path("postal")
public class PostalResource {

    @EJB
    private PostalBean postalBean;

    @Deprecated
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("calculate")
    public Response calculate(AuspostPostageFeeGet auspostPostageGet) {
        JsonObject response = postalBean.calculatePostageCostJson(auspostPostageGet);
        return Response.ok(response, MediaType.APPLICATION_JSON).build();
    }

    @Deprecated
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("calculate_reg_test")
    public Response calculateRegularTest() {
        postalBean.calculatePostageCost(32, 800, 9999, "AUS_PARCEL_REGULAR");
        return Response.ok().build();
    }

    @Deprecated
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("calculate_exp_test")
    public Response calculateExpressTest() {
        postalBean.calculatePostageCost(32, 800, 9999, "AUS_PARCEL_EXPRESS");
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("state/{postcode}")
    public Response fetchState(@PathParam("postcode") String postcodeString) {
        int postcode;

        try {
            postcode = Integer.parseInt(postcodeString);
        } catch (NumberFormatException ex) {
            return Response.status(422).entity(Json.createObjectBuilder().add("error", "Invalid postcode. Postcode must be numeric.").build()).build();
        }
        if (postcodeString.length() != 4) {
            return Response.status(422).entity(Json.createObjectBuilder().add("error", "Invalid postcode. Postcode must be four digits.").build()).build();
        }
        SerialResponse response = postalBean.getStateName(postcode);
        switch (response.getStatusCode()) {
            case 200:
                return Response.ok(response.getBody(), MediaType.APPLICATION_JSON).build();
            case 404:
                return Response.status(404).entity(response.getBody()).build();
            default:
                return Response.status(500).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("postcode/{suburb}")
    public Response searchPostcodes(@PathParam("suburb") String suburb) {

        SerialResponse response = postalBean.searchPostcodes(suburb);
        switch (response.getStatusCode()) {
            case 200:
                return Response.ok(response.getBody(), MediaType.APPLICATION_JSON).build();
            case 404:
                return Response.status(404).entity(response.getBody()).build();
            default:
                return Response.status(500).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("suburb/detail/{suburb}")
    public Response searchSuburbDetail(@PathParam("suburb") String suburb) {

        SerialResponse response = postalBean.searchSuburbDetail(suburb);
        switch (response.getStatusCode()) {
            case 200:
                return Response.ok(response.getBody(), MediaType.APPLICATION_JSON).build();
            case 404:
                return Response.status(404).entity(response.getBody()).build();
            default:
                return Response.status(500).build();
        }
    }
}
