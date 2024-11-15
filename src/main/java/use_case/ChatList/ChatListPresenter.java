package interface_adapters.ChatList;

import entity.ChatEntry;
import use_case.ChatList.ChatListOutputBoundary;

import java.util.List;

public class ChatListPresenter implements ChatListOutputBoundary {
    @Override
    public void presentChatList(List<ChatEntry> chatList) {
        System.out.println("Chat List:");
        for (ChatEntry chat : chatList) {
            System.out.println(chat.getName() + " - " + chat.getLastMessagePreview() +
                    " at " + chat.getLastMessageTime());
        }
    }

    @Override
    public void presentError(String errorMessage) {
        System.out.println("Error: " + errorMessage);
    }
}

