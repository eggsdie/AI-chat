package use_case.send_message;

import use_case.enter_chat.EnterChatOutputBoundary;
import use_case.enter_chat.EnterChatUserDataAccessInterface;

public class SendMessageInteracter implements SendMessageInputBoundary {
    private final SendMessageUserDataAccessInterface userDataAccessObject;

    public SendMessageInteracter(SendMessageUserDataAccessInterface userDataAccessObject) {
        this.userDataAccessObject = userDataAccessObject;
    }

    @Override
    public void execute(SendMessageInputData inputData) {
        userDataAccessObject.addMessage(inputData.getSender(), inputData.getReceiver(), inputData.getReceiver());
    }
}

