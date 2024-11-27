package use_case.enter_chat;

import entity.ChatEntry;
import entity.Message;

import java.util.ArrayList;

public class EnterChatInteractor implements EnterChatInputBoundary {
    private final EnterChatUserDataAccessInterface userDataAccessObject;
    private final EnterChatOutputBoundary userPresenter;

    public EnterChatInteractor(EnterChatUserDataAccessInterface userDataAccessObject,
                               EnterChatOutputBoundary enterChatOutputBoundary) {
        this.userPresenter = enterChatOutputBoundary;
        this.userDataAccessObject = userDataAccessObject;
    }

    @Override
    public void execute(EnterChatInputData inputData) {
        final String sender = inputData.getSender();
        final String receiver = inputData.getReceiver();
        final ArrayList<Message> messages = userDataAccessObject.messagesByChat(sender, receiver);
        final EnterChatOutputData enterChatOutputData = new EnterChatOutputData(false,
                sender, receiver, messages);
        userPresenter.prepareSuccessView(enterChatOutputData);
    }

    @Override
    public void switchToChatListView() {
        userPresenter.switchToChatListView();
    }
}

