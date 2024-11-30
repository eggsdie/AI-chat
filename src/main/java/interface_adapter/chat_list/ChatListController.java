package interface_adapter.chat_list;

import entity.User;
import use_case.chat_list.ChatListInputBoundary;
import use_case.chat_list.ChatListInputData;

public class ChatListController {

    private final ChatListInputBoundary chatListUseCaseInteractor;

    public ChatListController(ChatListInputBoundary chatListUseCaseInteractor) {
        this.chatListUseCaseInteractor = chatListUseCaseInteractor;
    }

    public void execute(User currentUser, String otherUser, String messagePreview) {
        final ChatListInputData chatListInputData = new ChatListInputData(currentUser, otherUser);

        chatListUseCaseInteractor.execute(chatListInputData, messagePreview);
    }

    /*public boolean removeChat(String name) {
        return chatListUseCaseInteractor.removeChat(name);
    }*/

}
