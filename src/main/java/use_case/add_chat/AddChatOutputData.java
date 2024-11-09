package use_case.add_chat;

public class AddChatOutputData {

    private final boolean useCaseFailed;

    public AddChatOutputData(boolean useCaseFailed) {
        this.useCaseFailed = useCaseFailed;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
