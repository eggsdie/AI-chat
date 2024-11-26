package data_access;

import okhttp3.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DemoRestfulApi {

    private OkHttpClient client;
    private final String url = "https://csc207-restfulapi-115940950435.us-central1.run.app/";

    public DemoRestfulApi() {
        this.client = new OkHttpClient();
    }

    public String getAllUsers() {
        // Define the URL
        String addedTag = "users";
        String completeUrl = url + addedTag;

        // Build the GET request
        Request request = new Request.Builder()
                .url(completeUrl)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            }
            else {
                return "Request failed with status code: " + response.code();
            }
        }
        catch (Exception e) {
            return "Request failed due to an exception: " + e.getMessage();
        }
    }

    public JsonObject getAllUsersJSON() {
        String addedTag = "users";
        String completeUrl = url + addedTag;

        Request request = new Request.Builder()
                .url(completeUrl)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return JsonParser.parseString(response.body().string()).getAsJsonObject();
            } else {
                JsonObject errorJson = new JsonObject();
                errorJson.addProperty("error", "Request failed");
                errorJson.addProperty("statusCode", response.code());
                return errorJson;
            }
        } catch (Exception e) {
            JsonObject exceptionJson = new JsonObject();
            exceptionJson.addProperty("error", "Request failed due to an exception");
            exceptionJson.addProperty("message", e.getMessage());
            return exceptionJson;
        }
    }

    public String createNewUser(String userId, String userName, String password, String email, String firstName, String lastName) {
        // Define the URL
        String addedTag = "users";
        String completeUrl = url + addedTag;

        String createDate = convertDateObjectToString(new Date());
        String updateDate = createDate;

        // Create JSON object for the new user
        String json = String.format("{\"userId\":\"%s\",\"userName\":\"%s\",\"password\":\"%s\",\"email\":\"%s\",\"firstName\":\"%s\",\"lastName\":\"%s\", \"createDate\":\"%s\", \"updateDate\":\"%s\"}", userId, userName, password, email, firstName, lastName, createDate, updateDate);

        // Create the RequestBody
        RequestBody body = RequestBody.create(
                json,
                MediaType.get("application/json; charset=utf-8")
        );

        // Build the POST request
        Request request = new Request.Builder()
                .url(completeUrl)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            }
            else {
                return "Request failed with status code: " + response.code();
            }
        }
        catch (Exception e) {
            return "Request failed due to an exception: " + e.getMessage();
        }
    }

    public String updateUser(String userId, String userName, String password, String email, String firstName, String lastName) {
        String addedTag = "users/" + userId;
        String completeUrl = url + addedTag;

        String createDate = convertDateObjectToString(new Date());
        String updateDate = createDate;

        String json = String.format("{\"userId\":\"%s\",\"userName\":\"%s\",\"password\":\"%s\",\"email\":\"%s\",\"firstName\":\"%s\",\"lastName\":\"%s\", \"createDate\":\"%s\", \"updateDate\":\"%s\"}", userId, userName, password, email, firstName, lastName, createDate, updateDate);
        RequestBody body = RequestBody.create(
                json,
                MediaType.get("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(completeUrl)
                .put(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            }
            else {
                return "Request failed with status code: " + response.code();
            }
        }
        catch (Exception e) {
            return "Request failed due to an exception: " + e.getMessage();
        }
    }

    public String deleteUser(String userId) {
        String addedTag = "users/" + userId;
        String completeUrl = url + addedTag;

        Request request = new Request.Builder()
                .url(completeUrl)
                .delete()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return "User deleted successfully";
            }
            else {
                return "Request failed with status code: " + response.code();
            }
        }
        catch (Exception e) {
            return "Request failed due to an exception: " + e.getMessage();
        }
    }

    // Create a new message (C)
    public String createNewMessage(String messageId, String messageContent, String senderId, String receiverId) {
        String addedTag = "messages";
        String completeUrl = url + addedTag;

        String createDate = convertDateObjectToString(new Date());
        String updateDate = createDate;

        // Create JSON object for the message
        String json = String.format(
                "{\"messageId\":\"%s\",\"createDate\":\"%s\",\"updateDate\":\"%s\",\"messageContent\":\"%s\",\"senderId\":\"%s\",\"receiverId\":\"%s\"}", messageId, createDate, updateDate, messageContent, senderId, receiverId
        );

        RequestBody body = RequestBody.create(
                json,
                MediaType.get("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(completeUrl)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            } else {
                return "Request failed with status code: " + response.code();
            }
        } catch (Exception e) {
            return "Request failed due to an exception: " + e.getMessage();
        }
    }

    // Retrieve all messages (R)
    public String getAllMessages() {
        String addedTag = "messages";
        String completeUrl = url + addedTag;

        Request request = new Request.Builder()
                .url(completeUrl)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            } else {
                return "Request failed with status code: " + response.code();
            }
        } catch (Exception e) {
            return "Request failed due to an exception: " + e.getMessage();
        }
    }

    public JsonObject getAllMessagesJSON() {
        String addedTag = "messages";
        String completeUrl = url + addedTag;

        Request request = new Request.Builder()
                .url(completeUrl)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return JsonParser.parseString(response.body().string()).getAsJsonObject();
            } else {
                JsonObject errorJson = new JsonObject();
                errorJson.addProperty("error", "Request failed");
                errorJson.addProperty("statusCode", response.code());
                return errorJson;
            }
        } catch (Exception e) {
            JsonObject exceptionJson = new JsonObject();
            exceptionJson.addProperty("error", "Request failed due to an exception");
            exceptionJson.addProperty("message", e.getMessage());
            return exceptionJson;
        }
    }

    // Update a message (U)
    public String updateMessage(String messageId, String messageContent) {
        String addedTag = "messages/" + messageId;
        String completeUrl = url + addedTag;

        String updateDate = convertDateObjectToString(new Date());

        // Create JSON object for the updated message content
        String json = String.format(
                "{\"messageContent\":\"%s\", \"updateDate\":\"s\"}",
                messageId, messageContent, updateDate
        );

        RequestBody body = RequestBody.create(
                json,
                MediaType.get("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(completeUrl)
                .put(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            } else {
                return "Request failed with status code: " + response.code();
            }
        } catch (Exception e) {
            return "Request failed due to an exception: " + e.getMessage();
        }
    }

    // Delete a message (D)
    public String deleteMessage(String messageId) {
        String addedTag = "messages/" + messageId;
        String completeUrl = url + addedTag;

        Request request = new Request.Builder()
                .url(completeUrl)
                .delete()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return "Message deleted successfully";
            } else {
                return "Request failed with status code: " + response.code();
            }
        } catch (Exception e) {
            return "Request failed due to an exception: " + e.getMessage();
        }
    }

    public String convertDateObjectToString(Date date) {

        // Convert Date to ZonedDateTime in UTC
        ZonedDateTime zonedDateTime = date.toInstant().atZone(ZoneOffset.UTC);

        // Format ZonedDateTime to ISO-8601 string with offset
        String formattedDate = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));

        return formattedDate;

    }

    public String convertLocalDateTimeObjectToString(LocalDateTime localDateTime) {
        // Convert LocalDateTime to ZonedDateTime in UTC
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneOffset.UTC);

        // Format ZonedDateTime to ISO-8601 string with offset
        String formattedDate = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));

        return formattedDate;
    }

}
