package use_case.ChatList;

import entity.ChatEntry;
import java.util.ArrayList;
import java.util.List;

public class ChatListManager {
    private List<ChatEntry> chatList;

    public ChatListManager() {
        this.chatList = new ArrayList<>();
    }

    // Add a new chat entry
    public boolean addChat(String name, String messagePreview) {
        if (name == null || name.isEmpty()) return false;

        for (ChatEntry chat : chatList) {
            if (chat.getName().equalsIgnoreCase(name)) {
                return false; // Prevent duplicate chats
            }
        }

        chatList.add(new ChatEntry(name, java.time.LocalTime.now(), messagePreview));
        return true;
    }

    // Remove a chat entry
    public boolean removeChat(String name) {
        return chatList.removeIf(chat -> chat.getName().equalsIgnoreCase(name));
    }

    // Retrieve all chats
    public List<ChatEntry> getAllChats() {
        return new ArrayList<>(chatList);
    }

    // Find a chat by name
    public ChatEntry findChat(String name) {
        for (ChatEntry chat : chatList) {
            if (chat.getName().equalsIgnoreCase(name)) {
                return chat;
            }
        }
        return null;
    }
}

