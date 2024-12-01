package use_case.login;

import entity.ChatEntry;
import entity.User;

import java.util.ArrayList;

/**
 * Output Data for the Login Use Case.
 */
public class LoginOutputData {

    private final String username;
    private final boolean useCaseFailed;
    private final User user;
    private final ArrayList<ChatEntry> chatEntries;

    public LoginOutputData(String username, ArrayList<ChatEntry> chatEntries, User user, boolean useCaseFailed) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
        this.user = user;
        this.chatEntries = chatEntries;
    }

    public String getUsername() {
        return username;
    }

    public User getUser() {
        return user;
    }

    public ArrayList<ChatEntry> getChatEntries() {
        return chatEntries;
    }
}
