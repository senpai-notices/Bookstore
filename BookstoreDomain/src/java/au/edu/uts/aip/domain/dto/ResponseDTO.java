package au.edu.uts.aip.domain.dto;

/**
 *The needed libraries
 */
import java.io.Serializable;
import javax.json.JsonObject;

/**
 * ResponseDTO is a DTO class that is used to retrieve the JSON object and the status
 * It is a data transfer object 
 * 
 * It has properties: 
 * body: JsonObject object
 * statusCode: Integer value
 * The attributes are not null and accessed by get and set methods
 * 
 *  @author team San Dang, Alex Tan, Xiaoyang Liu
 */
// good name?
public class ResponseDTO implements Serializable {

    /**
     * the Json object
     */
    private JsonObject body;
    /**
     * the code of the status
     */
    private int statusCode;

    /**
     * constructor of the ResponseDTO class 
     * two parameters
     */
    public ResponseDTO(JsonObject body, int statusCode) {
        this.body = body;
        this.statusCode = statusCode;
    }

    /**
    * {@link ResponseDTO#body}
    * data type: JsonObject
    */
    public JsonObject getBody() {
        return body;
    }

    public void setBody(JsonObject body) {
        this.body = body;
    }

    /**
    * {@link ResponseDTO#statusCode}
    * data type: Integer
    * return an int Value
    */
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
