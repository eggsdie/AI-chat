package use_case.send_message;

public class SendMessageInteracter implements SendMessageInputBoundary {

    @Override
    public void execute(SendMessageInputData inputData) {
        inputData.getChatEntry().addMessage(inputData.getMessage());
    }
}

