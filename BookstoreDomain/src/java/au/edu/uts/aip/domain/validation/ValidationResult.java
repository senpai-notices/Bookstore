package au.edu.uts.aip.domain.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
public class ValidationResult {

    private final ArrayList<String> errorMessages;
    private final HashMap<String, String> formErrors;

    /**
     *
     */
    public ValidationResult() {
        this.formErrors = new HashMap<>();
        this.errorMessages = new ArrayList<>();
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getErrorMessages() {
        return errorMessages;
    }

    /**
     *
     * @return
     */
    public HashMap<String, String> getFormErrors() {
        return formErrors;
    }

    /**
     *
     * @param error
     */
    public void addError(String error) {
        errorMessages.add(error);
    }

    /**
     *
     * @param formName
     * @param error
     */
    public void addFormError(String formName, String error) {
        formErrors.put(formName, error);
    }

    /**
     *
     * @return
     */
    public JsonObject toJson() {
        JsonArrayBuilder errorsBuilder = Json.createArrayBuilder();
        for (String errorMessage : errorMessages) {
            errorsBuilder.add(errorMessage);
        }

        JsonObjectBuilder formErrorsBuilder = Json.createObjectBuilder();
        for (Map.Entry<String, String> formError : formErrors.entrySet()) {
            formErrorsBuilder.add(formError.getKey(), formError.getValue());
        }

        JsonObjectBuilder resultBuilder = Json.createObjectBuilder();
        resultBuilder.add("errors", errorsBuilder.build());
        resultBuilder.add("formErrors", formErrorsBuilder.build());

        return resultBuilder.build();
    }
}
