package au.edu.uts.aip.domain.pin.utility;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class PinResponseUtility {
    
    public static JsonObject toJson(String s) {
        JsonObject jsonObject;
        
        try (JsonReader reader = Json.createReader(new StringReader(s))) {
            jsonObject = reader.readObject();
        }
        
        return jsonObject;
    }
}
