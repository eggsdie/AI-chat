package interface_adapter.send_message;

import use_case.send_message.SendMessageInputBoundary;
import use_case.send_message.SendMessageInputData;

public class SendMessageController {

    private final SendMessageInputBoundary sendMessageUseCaseInteractor;

    public SendMessageController(SendMessageInputBoundary sendMessageUseCaseInteractor) {
        this.sendMessageUseCaseInteractor = sendMessageUseCaseInteractor;
    }

    public void execute(String sender, String receiver, String message) {
        final SendMessageInputData sendMessageInputData = new SendMessageInputData(sender, receiver, message);

        sendMessageUseCaseInteractor.execute(sendMessageInputData);
    }
}
