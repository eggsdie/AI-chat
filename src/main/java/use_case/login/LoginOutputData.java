package use_case.login;

import entity.ChatEntry;

import java.util.ArrayList;

/**
 * Output Data for the Login Use Case.
 */
public class LoginOutputData {

    private final String username;
    private final boolean useCaseFailed;
    private final ArrayList<ChatEntry> chatEntries;

    public LoginOutputData(String username, ArrayList<ChatEntry> chatEntries, boolean useCaseFailed) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
        this.chatEntries = chatEntries;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<ChatEntry> getChatEntries() {
        return chatEntries;
    }
}
