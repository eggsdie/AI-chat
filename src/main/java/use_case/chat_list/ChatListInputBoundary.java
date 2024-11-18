package use_case.chat_list;

public interface ChatListInputBoundary {
    void addChat(ChatListInputData chatListInputData, String messagePreview);
    boolean removeChat(String name);

}

