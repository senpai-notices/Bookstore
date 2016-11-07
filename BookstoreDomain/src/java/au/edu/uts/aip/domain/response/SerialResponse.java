package au.edu.uts.aip.domain.response;

import java.io.Serializable;
import javax.json.JsonObject;

/**
 * SerialResponse is a container used in lieu of javax.ws.rs.Core.Response in order send responses
 * that are serialised. It contains the request body, as a JsonObject and its status code. It has
 * properties: body: JsonObject object statusCode: Integer value The attributes are not null and
 * accessed by get and set methods
 *
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
public class SerialResponse implements Serializable {

    /**
     * the Json object of the response body
     */
    private JsonObject body;
    /**
     * the status code of the response
     */
    private int statusCode;

    /**
     * constructor of the ResponseDTO class two parameters
     *
     * @param body
     * @param statusCode
     */
    public SerialResponse(JsonObject body, int statusCode) {
        this.body = body;
        this.statusCode = statusCode;
    }

    /**
     * {@link SerialResponse#body} data type: JsonObject
     */
    public JsonObject getBody() {
        return body;
    }

    public void setBody(JsonObject body) {
        this.body = body;
    }

    /**
     * {@link SerialResponse#statusCode} data type: Integer return an int Value
     */
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
