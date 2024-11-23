package interface_adapter.enter_chat;

import entity.ChatEntry;
import use_case.chat_list.ChatListInputBoundary;
import use_case.chat_list.ChatListInputData;
import use_case.enter_chat.EnterChatInputBoundary;
import use_case.enter_chat.EnterChatInputData;

public class EnterChatController {

    private final EnterChatInputBoundary enterChatUseCaseInteractor;

    public EnterChatController(EnterChatInputBoundary enterChatUseCaseInteractor) {
        this.enterChatUseCaseInteractor = enterChatUseCaseInteractor;
    }

    public void execute(ChatEntry chatEntry) {
        final EnterChatInputData enterChatInputData = new EnterChatInputData(chatEntry);

        enterChatUseCaseInteractor.execute(enterChatInputData);
    }

    public void switchToChatListView() {
        enterChatUseCaseInteractor.switchToChatListView();
    }

}
