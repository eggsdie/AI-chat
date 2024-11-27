package data_access;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.swing.plaf.synth.SynthTextAreaUI;

public class HelperMethods {

    /**
     * Parses the given JSON array and retrieves the JSON object specific to the provided username.
     *
     * @param jsonArray The input JSON array to parse.
     * @param username  The username to search for.
     * @return A JSON object containing information specific to the provided username.
     */
    public static JsonObject getJsonObjectForUsername(JsonArray jsonArray, String username) {
        for (JsonElement element : jsonArray) {
            JsonObject userObject = element.getAsJsonObject();
            if (userObject.has("userName") && userObject.get("userName").getAsString().equals(username)) {
                return userObject;
            }
        }
        // Return an empty JSON object if username is not found
        JsonObject emptyObject = new JsonObject();
        emptyObject.addProperty("error", "Username not found");
        return emptyObject;
    }

    public static JsonObject getJsonObjectForId(JsonArray jsonArray, String id) {
        for (JsonElement element : jsonArray) {
            JsonObject userObject = element.getAsJsonObject();
            if (userObject.has("userId") && userObject.get("userId").getAsString().equals(id)) {
                return userObject;
            }
        }
        // Return an empty JSON object if id is not found
        JsonObject emptyObject = new JsonObject();
        emptyObject.addProperty("error", "ID not found");
        return emptyObject;
    }
}
