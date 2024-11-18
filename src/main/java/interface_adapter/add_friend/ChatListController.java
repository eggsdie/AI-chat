package interface_adapter.add_friend;

import use_case.ChatList.ChatListInputBoundary;
import use_case.ChatList.ChatListInputData;

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

    /**
     * Executes the "switch to LoginView" Use Case.
     */
//    public void switchToLoggedInView() {
//        chatListUseCaseInteractor.switchToLoggedInView();
//    }

}
