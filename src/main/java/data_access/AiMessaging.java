package data_access;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.cohere.api.Cohere;
import com.cohere.api.resources.v2.requests.V2ChatRequest;
import com.cohere.api.types.ChatMessageV2;
import com.cohere.api.types.ChatResponse;
import com.cohere.api.types.UserMessage;
import com.cohere.api.types.UserMessageContent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONArray;

public class AiMessaging {

    public AiMessaging() {

    }

    final OpenaiApiCall openaiApiCall = new OpenaiApiCall();
    final DemoRestfulApi demoRestfulApi = new DemoRestfulApi();

    public String generateNewMessage(String username1, String username2) {

        JsonArray response = this.demoRestfulApi.getAllMessagesJSON();
        JsonArray recentUserMessages = filterLastFiveMessages(response, username1, username2);
        String filteredMessages = extractUsernameAndMessages(recentUserMessages);
        System.out.println(filteredMessages);
        String finalResponse = this.openaiApiCall.generateResponse("Messages are sorted from most recent to least recent. Generate another message in the conversation. You are to respond in exactly this format as user " + username1 + ". 'Content:<message_content_string>'. Do not include any other information other than the message content." + filteredMessages, username1);
        //System.out.println(finalResponse);
        return finalResponse;
    }

    public static JsonArray filterLastFiveMessages(JsonArray messages, String username1, String username2) {
        // List to store filtered messages
        List<JsonObject> filteredMessages = new ArrayList<>();

        // Filter messages sent between the two users
        for (JsonElement element : messages) {
            JsonObject message = element.getAsJsonObject();
            String sender = message.get("senderUsername").getAsString();
            String receiver = message.get("receiverUsername").getAsString();

            if ((sender.equals(username1) && receiver.equals(username2)) ||
                    (sender.equals(username2) && receiver.equals(username1))) {
                filteredMessages.add(message);
            }
        }

        // Sort the filtered messages by `createDate` in descending order
        filteredMessages.sort(Comparator.comparing(m -> m.get("createDate").getAsString(), Comparator.reverseOrder()));

        // Get the last five messages or fewer
        JsonArray lastFiveMessages = new JsonArray();
        for (int i = 0; i < Math.min(5, filteredMessages.size()); i++) {
            lastFiveMessages.add(filteredMessages.get(i));
        }

        return lastFiveMessages;
    }

    public static String extractUsernameAndMessages(JsonArray messages) {
        StringBuilder result = new StringBuilder();

        // Iterate through the original messages array
        for (JsonElement element : messages) {
            JsonObject message = element.getAsJsonObject();
            String username = message.get("senderUsername").getAsString();
            String content = message.get("messageContent").getAsString();
            String createDate = message.get("createDate").getAsString(); // Get createDate field

            // Append the formatted string to the result
            result.append("(")
                    .append(username)
                    .append("):(")
                    .append(content)
                    .append("):(")
                    .append(createDate)
                    .append(")");
        }

        // Return the resulting string
        return result.toString();
    }

}
