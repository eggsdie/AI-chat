package interface_adapter.chat_list;

import use_case.chat_list.ChatListInputBoundary;
import use_case.chat_list.ChatListInputData;

public class ChatListController {

    private final ChatListInputBoundary chatListUseCaseInteractor;

    public ChatListController(ChatListInputBoundary chatListUseCaseInteractor) {
        this.chatListUseCaseInteractor = chatListUseCaseInteractor;
    }

    public void addChat(String name, String messagePreview) {
        final ChatListInputData chatListInputData = new ChatListInputData(name);

        chatListUseCaseInteractor.addChat(chatListInputData, messagePreview);
    }

    public boolean removeChat(String name) {
        return chatListUseCaseInteractor.removeChat(name);
    }

}
