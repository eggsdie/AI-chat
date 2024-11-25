package data_access;

import com.cohere.api.Cohere;
import com.cohere.api.resources.v2.requests.V2ChatRequest;
import com.cohere.api.types.*;
import java.util.List;

public class AiMessaging {
    public static void main(String[] args) {
        Cohere cohere = Cohere.builder().token("fRLgN2Hp8Q").clientName("snippet").build();

        ChatResponse response =
                cohere.v2()
                        .chat(
                                V2ChatRequest.builder()
                                        .model("command-r-plus")
                                        .messages(
                                                List.of(
                                                        ChatMessageV2.user(
                                                                UserMessage.builder()
                                                                        .content(
                                                                                UserMessageContent
                                                                                        .of("Please analyze these messages between 2 people and give an example response."))
                                                                        .build())))
                                        .build());

        System.out.println(response);
    }
}
