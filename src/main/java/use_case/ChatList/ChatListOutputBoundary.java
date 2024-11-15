package use_case.ChatList;

import entity.ChatEntry;
import java.util.List;

public interface ChatListOutputBoundary {
    void presentChatList(List<ChatEntry> chatList);
    void presentError(String errorMessage);
}

