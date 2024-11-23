package use_case.enter_chat;

import entity.ChatEntry;

public class EnterChatInteractor implements EnterChatInputBoundary {
    private final EnterChatOutputBoundary userPresenter;

    public EnterChatInteractor(EnterChatOutputBoundary enterChatOutputBoundary) {
        this.userPresenter = enterChatOutputBoundary;
    }

    @Override
    public void execute(EnterChatInputData inputData) {
        final ChatEntry chatEntry = inputData.getChatEntry();
        final EnterChatOutputData enterChatOutputData = new EnterChatOutputData(false,
                chatEntry);
        userPresenter.prepareSuccessView(enterChatOutputData);
    }

    @Override
    public void switchToChatListView() {
        userPresenter.switchToChatListView();
    }
}

