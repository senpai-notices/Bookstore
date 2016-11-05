package au.edu.uts.aip.domain.pin.util;

import au.edu.uts.aip.domain.validation.ValidationResult;
import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

public class PinResponseUtil {

    @Deprecated // TODO: Use domain.utility.ApiResponseUtil.toJson() instead.
    public static JsonObject toJson(String jsonString) {
        JsonObject jsonObject;

        try (JsonReader reader = Json.createReader(new StringReader(jsonString))) {
            jsonObject = reader.readObject();
        }

        return jsonObject;
    }

    public static ValidationResult validate(int statusCode, JsonObject response) {
        ValidationResult validationResult = new ValidationResult();

        switch (statusCode / 100) {
            case 1:
            case 2:
                return null;
            case 4:
                if (response.containsKey("messages")) {
                    JsonArray errorMessages = response.getJsonArray("messages");

                    for (int i = 0; i < errorMessages.size(); i++) {
                        validationResult.addFormError(
                                errorMessages.getJsonObject(i).getString("param"),
                                errorMessages.getJsonObject(i).getString("message"));
                    }
                } else {
                    validationResult
                            .addError(response.getString("error_description", "Client error"));
                }
                return validationResult;
            case 5:
                validationResult
                        .addError(response.getString("error_description", "Pin server error"));
                return validationResult;
            default:
                return null;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="unused">
    public static boolean is200Code(int responseCode) {
        Family family = Response.Status.Family.familyOf(responseCode);
        if (family == Response.Status.Family.SUCCESSFUL) {
            return true;
        }
        return false;
    }

    public static boolean is400Code(int responseCode) {
        Family family = Response.Status.Family.familyOf(responseCode);
        if (family == Response.Status.Family.CLIENT_ERROR) {
            return true;
        }
        return false;
    }

    public static boolean is500Code(int responseCode) {
        Family family = Response.Status.Family.familyOf(responseCode);
        if (family == Response.Status.Family.SERVER_ERROR) {
            return true;
        }
        return false;
    }
    // </editor-fold>
}
