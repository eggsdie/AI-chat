package data_access;

import com.cohere.api.Cohere;
import com.cohere.api.resources.v2.requests.V2ChatRequest;
import com.cohere.api.types.ChatMessageV2;
import com.cohere.api.types.ChatResponse;
import com.cohere.api.types.UserMessage;
import com.cohere.api.types.UserMessageContent;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class AiMessaging {
    public static void main(String[] args) {
        Cohere cohere = Cohere.builder()
                .token("iEYhPtjIKUCv8VJlN20XDUvMn5gDsUvsXZljUrgb")
                .clientName("snippet")
                .build();

        try {
            // Make the API call
            ChatResponse rawResponse = cohere.v2().chat(
                    V2ChatRequest.builder()
                            .model("command-r-plus")
                            .messages(
                                    List.of(
                                            ChatMessageV2.user(
                                                    UserMessage.builder()
                                                            .content(UserMessageContent.of("Say something funny"))
                                                            .build())))
                            .build());

            // Serialize the response back to JSON
            ObjectMapper mapper = new ObjectMapper();
            String rawJson = mapper.writeValueAsString(rawResponse);

            // Preprocess the JSON to replace "COMPLETE" with "complete"
            rawJson = rawJson.replace("\"COMPLETE\"", "\"complete\"");

            // Deserialize the corrected JSON back into the ChatResponse object
            ChatResponse processedResponse = mapper.readValue(rawJson, ChatResponse.class);

            // Print the processed response
            System.out.println(processedResponse);

        } catch (IOException e) {
            System.err.println("Error during JSON preprocessing or API call: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}
