package use_case.send_message;

public class SendMessageInteracter implements SendMessageInputBoundary {
    private final SendMessageUserDataAccessInterface userDataAccessObject;

    public SendMessageInteracter(SendMessageUserDataAccessInterface userDataAccessObject) {
        this.userDataAccessObject = userDataAccessObject;
    }

    @Override
    public void execute(SendMessageInputData inputData) {
        userDataAccessObject.addMessage(inputData.getSender(), inputData.getReceiver(), inputData.getContent());
    }
}

