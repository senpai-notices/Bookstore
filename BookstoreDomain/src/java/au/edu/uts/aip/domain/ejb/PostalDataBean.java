package au.edu.uts.aip.domain.ejb;

import au.edu.uts.aip.domain.remote.PostalDataRemote;
import au.edu.uts.aip.domain.response.SerialResponse;
import au.edu.uts.aip.domain.entity.Suburb;
import java.util.List;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.Response;

/**
 * @inheritDoc
 */
@Stateless
public class PostalDataBean implements PostalDataRemote {

    @PersistenceContext
    private EntityManager em;

    /**
     * @inheritDoc
     */
    @Override
    public SerialResponse getStateName(int postcode) {
        if ((postcode >= 1000 && postcode <= 2599)
                || (postcode >= 2620 && postcode <= 2899)
                || (postcode >= 2921 && postcode <= 2999)) {
            return new SerialResponse(JSON_STATE_NSW, Response.Status.OK.getStatusCode());
        } else if ((postcode >= 200 && postcode <= 299)
                || (postcode >= 2600 && postcode <= 2619)
                || (postcode >= 2900 && postcode <= 2920)) {
            return new SerialResponse(JSON_STATE_ACT, Response.Status.OK.getStatusCode());
        } else if ((postcode >= 3000 && postcode <= 3999)
                || (postcode >= 8000 && postcode <= 8999)) {
            return new SerialResponse(JSON_STATE_VIC, Response.Status.OK.getStatusCode());
        } else if ((postcode >= 4000 && postcode <= 4999)
                || (postcode >= 9000 && postcode <= 9999)) {
            return new SerialResponse(JSON_STATE_QLD, Response.Status.OK.getStatusCode());
        } else if (postcode >= 5000 && postcode <= 5999) {
            return new SerialResponse(JSON_STATE_SA, Response.Status.OK.getStatusCode());
        } else if ((postcode >= 6000 && postcode <= 6797)
                || (postcode >= 6800 && postcode <= 6999)) {
            return new SerialResponse(JSON_STATE_WA, Response.Status.OK.getStatusCode());
        } else if (postcode >= 7000 && postcode <= 7999) {
            return new SerialResponse(JSON_STATE_TAS, Response.Status.OK.getStatusCode());
        } else if (postcode >= 800 && postcode <= 999) {
            return new SerialResponse(JSON_STATE_NT, Response.Status.OK.getStatusCode());
        } else {
            return new SerialResponse(JSON_STATE_NOT_FOUND, Response.Status.NOT_FOUND.getStatusCode());
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public SerialResponse searchPostcodes(String suburb) {
        // TODO: remove if trimming in frontend.
        suburb = suburb.trim();

        // Build criteria query returning Suburbs      
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Suburb> cq = cb.createQuery(Suburb.class);

        // Set up the query       
        Root<Suburb> root = cq.from(Suburb.class);
        cq.where(cb.like(root.<String>get("name"), cb.parameter(String.class, "param")));
        TypedQuery<Suburb> tq = em.createQuery(cq);
        tq.setParameter("param", "%" + suburb + "%");

        List<Suburb> result = tq.getResultList();

        if (!result.isEmpty()) {
            JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

            for (Suburb s : result) {
                jsonArrayBuilder.add(s.getPostcode());
            }
            JsonObject resultJson = jsonBuilder.add("suburbs", jsonArrayBuilder).build();

            return new SerialResponse(resultJson, Response.Status.OK.getStatusCode());
        }
        return new SerialResponse(JSON_SUBURB_NOT_FOUND, Response.Status.NOT_FOUND.getStatusCode());
    }

    /**
     * @inheritDoc
     */
    @Override
    public SerialResponse searchSuburbDetail(String suburb) {
        // TODO: remove if trimming in frontend.
        suburb = suburb.trim();

        // Build criteria query returning Suburbs      
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Suburb> cq = cb.createQuery(Suburb.class);

        // Set up the query       
        Root<Suburb> root = cq.from(Suburb.class);
        cq.where(cb.like(root.<String>get("name"), cb.parameter(String.class, "param")));
        TypedQuery<Suburb> tq = em.createQuery(cq);
        tq.setParameter("param", "%" + suburb + "%");

        List<Suburb> result = tq.getResultList();

        if (!result.isEmpty()) {
            JsonObjectBuilder outerJsonBuilder = Json.createObjectBuilder();
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

            for (Suburb s : result) {
                JsonObjectBuilder innerJsonBuilder = Json.createObjectBuilder()
                        .add("name", s.getName())
                        .add("state_name", s.getStateName())
                        .add("postcode", s.getPostcode());
                jsonArrayBuilder.add(innerJsonBuilder);
            }
            JsonObject resultJson = outerJsonBuilder.add("suburb_details", jsonArrayBuilder).build();

            return new SerialResponse(resultJson, Response.Status.OK.getStatusCode());
        }
        return new SerialResponse(JSON_SUBURB_NOT_FOUND, Response.Status.NOT_FOUND.getStatusCode());
    }
}
