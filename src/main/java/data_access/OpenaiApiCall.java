package data_access;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.IOException;

public class OpenaiApiCall {

    private final String API_KEY = "Hidden";
    private final String ENDPOINT = "https://api.openai.com/v1/chat/completions";

    private final OkHttpClient client = new OkHttpClient();

    public OpenaiApiCall() {

    }

    public String generateResponse(String content, String username) {
        // Correct the JSON payload structure, including temperature and top_p
        String jsonPayload = String.format("{"
                + "\"model\": \"gpt-4\","
                + "\"messages\": ["
                + "  {\"role\": \"system\", \"content\": \"%s\"},"
                + "  {\"role\": \"user\", \"content\": \"%s\"}"
                + "],"
                + "\"temperature\": 0.0,"  // Set temperature for reduced randomness
                + "\"top_p\": 0.1"        // Use top_p to consider all plausible completions
                + "}", "You are a user on a texting app. Your username is " + username + ". You are to only respond as " + username + ".", content);

        RequestBody body = RequestBody.create(jsonPayload, MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url(ENDPOINT)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                return extractContent(responseBody);
            } else {
                if (response.body() != null) {
                    // Handle error response
                    // System.err.println("Error Response Body: " + response.body().string());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "End";
    }


    public static String extractContent(String jsonResponse) {
        // Parse the JSON string into a JsonObject
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

        // Navigate to the "content" field
        String content = jsonObject
                .getAsJsonArray("choices")
                .get(0)
                .getAsJsonObject()
                .getAsJsonObject("message")
                .get("content")
                .getAsString();

        return content;
    }
}
