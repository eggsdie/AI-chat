package use_case.chat_list;

public interface ChatListInputBoundary {
    void execute(ChatListInputData chatListInputData, String messagePreview);
    // boolean removeChat(String name);

}

