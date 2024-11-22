package interface_adapters.ChatList;

import use_case.ChatList.ChatListInputBoundary;

public class ChatListController {
    private final ChatListInputBoundary chatListInputBoundary;

    public ChatListController(ChatListInputBoundary chatListInputBoundary) {
        this.chatListInputBoundary = chatListInputBoundary;
    }

    public boolean addChat(String name, String messagePreview) {
        return chatListInputBoundary.addChat(name, messagePreview);
    }

    public boolean removeChat(String name) {
        return chatListInputBoundary.removeChat(name);
    }
}
