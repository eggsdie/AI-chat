package data_access;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.MediaType;
import okhttp3.HttpUrl;

public class TranslatingApiCalls {
    public static void main(String[] args) {
        try {
            String apiKey = "YOUR_GOOGLE_TRANSLATE_API_KEY";
            String url = "https://translation.googleapis.com/language/translate/v2";

            // Prepare the request body
            String jsonBody = "{"
                    + "\"q\": \"Hello, world!\","
                    + "\"target\": \"es\""
                    + "}";

            MediaType JSON = MediaType.get("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(jsonBody, JSON);

            // HTTP/3-enabled client
            OkHttpClient client = new OkHttpClient();

            // Build the request
            Request request = new Request.Builder()
                    .url(HttpUrl.parse(url).newBuilder()
                            .addQueryParameter("key", apiKey)
                            .build())
                    .post(body)
                    .build();

            // Send the request
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    System.out.println("Response: " + responseBody);
                } else {
                    System.err.println("Request failed: " + response.code() + " " + response.message());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
