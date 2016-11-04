package au.edu.uts.aip.domain.util;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class ApiResponseUtil {

    public static JsonObject toJson(String jsonString) {
        JsonObject jsonObject;

        try (JsonReader reader = Json.createReader(new StringReader(jsonString))) {
            jsonObject = reader.readObject();
        }

        return jsonObject;
    }
}
