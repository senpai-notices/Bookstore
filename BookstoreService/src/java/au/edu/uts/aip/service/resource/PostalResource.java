package au.edu.uts.aip.service.resource;

import au.edu.uts.aip.domain.response.SerialResponse;
import au.edu.uts.aip.domain.remote.PostalDataRemote;
import au.edu.uts.aip.domain.remote.PostalFeeRemote;

import javax.ejb.EJB;
import javax.json.Json;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST endpoint for postal data access and postal fee calculation. Fee calculation uses Auspost fee
 * API.
 *
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@Path("postal")
public class PostalResource {

    @EJB
    private PostalFeeRemote postalFeeBean;
    @EJB
    private PostalDataRemote postalDataBean;

    /**
     * Gets the name of the state that the postcode is in.
     *
     * @param postcodeString
     * @return
     */
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
        SerialResponse response = postalDataBean.getStateName(postcode);
        switch (response.getStatusCode()) {
            case 200:
                return Response.ok(response.getBody(), MediaType.APPLICATION_JSON).build();
            case 404:
                return Response.status(404).entity(response.getBody()).build();
            default:
                return Response.status(500).build();
        }
    }

    /**
     * Searches possible postcodes for a given search suburb search string. One suburb may belong to
     * multiple postcodes.
     *
     * @param suburbSearchString
     * @return A list of postcode matches
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("postcode/{suburb}")
    public Response searchPostcodes(@PathParam("suburb") String suburbSearchString) {

        SerialResponse response = postalDataBean.searchPostcodes(suburbSearchString);
        switch (response.getStatusCode()) {
            case 200:
                return Response.ok(response.getBody(), MediaType.APPLICATION_JSON).build();
            case 404:
                return Response.status(404).entity(response.getBody()).build();
            default:
                return Response.status(500).build();
        }
    }

    /**
     * Searches possible postcodes for a given search suburb search string.
     * @param suburbSearchString
     * @return A list of suburb details that match.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("suburb/detail/{suburb}")
    public Response searchSuburbDetail(@PathParam("suburb") String suburbSearchString) {

        SerialResponse response = postalDataBean.searchSuburbDetail(suburbSearchString);
        switch (response.getStatusCode()) {
            case 200:
                return Response.ok(response.getBody(), MediaType.APPLICATION_JSON).build();
            case 404:
                return Response.status(404).entity(response.getBody()).build();
            default:
                return Response.status(500).build();
        }
    }

    /**
     * Calculate postal fee.
     * @param quantity Quantity of books.
     * @param from Origin postcode.
     * @param to Destination postcode.
     * @param type Service type. Either "normal" or "express".
     * @return The postal fee.
     */
    @POST
    @Path("calculate")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public double calculatePostalFee(@FormParam("quantity") int quantity,
            @FormParam("from") int from,
            @FormParam("to") int to,
            @FormParam("type") String type) {
        String serviceCode = "";
        if (type.equals("normal")) {
            serviceCode = "AUS_PARCEL_REGULAR";
        } else if (type.equals("express")) {
            serviceCode = "AUS_PARCEL_EXPRESS";
        } else {
            throw new RuntimeException("Invalid service code");
        }

        double cost = 0;
        while (quantity > 0) {
            cost += postalFeeBean.calculatePostageCost(Math.max(quantity, 30), from, to, serviceCode);
            quantity -= 30;
        }

        return cost;
    }
}
