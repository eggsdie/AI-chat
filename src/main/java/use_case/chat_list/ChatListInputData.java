package use_case.chat_list;

public class ChatListInputData {
    private final String username;
    private final String otherUser;

    public ChatListInputData(String username, String otherUser) {
        this.username = username;
        this.otherUser = otherUser;
    }

    public String getUsername() {
        return username;
    }

    public String getOtherUser() {
        return otherUser;
    }

}
