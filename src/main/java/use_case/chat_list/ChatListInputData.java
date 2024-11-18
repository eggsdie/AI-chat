package use_case.chat_list;

public class ChatListInputData {
    private final String username;

    public ChatListInputData(String username) {
        this.username = username;
    }

    public String getUser() {
        return username;
    }
}
