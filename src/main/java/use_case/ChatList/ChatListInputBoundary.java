package use_case.ChatList;

public interface ChatListInputBoundary {
    boolean addChat(String name, String messagePreview);
    boolean removeChat(String name);
}

