package au.edu.uts.aip.domain.dto;

import java.io.Serializable;
import javax.json.JsonObject;

// good name?
public class ResponseDTO implements Serializable {

    private JsonObject body;
    private int statusCode;

    public ResponseDTO(JsonObject body, int statusCode) {
        this.body = body;
        this.statusCode = statusCode;
    }

    public JsonObject getBody() {
        return body;
    }

    public void setBody(JsonObject body) {
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
