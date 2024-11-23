package use_case.enter_chat;

public interface EnterChatInputBoundary {
    void execute(EnterChatInputData enterChatInputData);

    void switchToChatListView();
}

