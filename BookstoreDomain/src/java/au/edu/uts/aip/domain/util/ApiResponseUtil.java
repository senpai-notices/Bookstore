package au.edu.uts.aip.domain.util;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 * Utility class for API responses
 * 
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
public class ApiResponseUtil {

    /**
     * Converts a JSON string into a JsonObject.
     * @param jsonString
     * @return
     */
    public static JsonObject toJson(String jsonString) {
        JsonObject jsonObject;

        try (JsonReader reader = Json.createReader(new StringReader(jsonString))) {
            jsonObject = reader.readObject();
        }

        return jsonObject;
    }
}
