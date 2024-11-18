package use_case.ChatList;

public interface ChatListInputBoundary {
    void addChat(ChatListInputData chatListInputData, String messagePreview);
    boolean removeChat(String name);

}

