package au.edu.uts.aip.domain.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * Container for form error messages
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
     * Get error messages that apply to the whole form.
     * @return
     */
    public ArrayList<String> getErrorMessages() {
        return errorMessages;
    }

    /**
     * Get error messages that apply to particular fields of a form.
     * @return
     */
    public HashMap<String, String> getFormErrors() {
        return formErrors;
    }

    /**
     * Add an error for the whole form.
     * @param error
     */
    public void addError(String error) {
        errorMessages.add(error);
    }

    /**
     * Add an error that applies to a certain form field.
     * @param formName
     * @param error
     */
    public void addFormError(String formName, String error) {
        formErrors.put(formName, error);
    }

    /**
     * Convert errors and formErrors into a JsonObject.
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
