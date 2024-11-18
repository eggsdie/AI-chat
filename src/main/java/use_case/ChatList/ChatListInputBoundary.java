package use_case.ChatList;

import use_case.add_friend.AddFriendInputData;

public interface ChatListInputBoundary {
    void addChat(ChatListInputData chatListInputData, String messagePreview);
    boolean removeChat(String name);

}

