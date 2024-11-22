package use_case.enter_chat;

public class EnterChatInteractor implements EnterChatInputBoundary {
    private final EnterChatOutputBoundary userPresenter;

    public EnterChatInteractor(EnterChatOutputBoundary enterChatOutputBoundary) {
        this.userPresenter = enterChatOutputBoundary;
    }

    @Override
    public void execute(EnterChatInputData inputData) {
        final EnterChatOutputData enterChatOutputData = new EnterChatOutputData(false,
                inputData.getChatEntry());
        userPresenter.prepareSuccessView(enterChatOutputData);
    }

}

