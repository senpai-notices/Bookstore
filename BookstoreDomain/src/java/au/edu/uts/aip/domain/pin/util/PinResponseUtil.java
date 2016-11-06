package au.edu.uts.aip.domain.pin.util;

import au.edu.uts.aip.domain.validation.ValidationResult;
import javax.json.JsonArray;
import javax.json.JsonObject;

public class PinResponseUtil {

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
}
