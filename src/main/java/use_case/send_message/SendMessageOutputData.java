package use_case.send_message;

public class SendMessageOutputData {

    private final boolean useCaseFailed;

    public SendMessageOutputData(boolean useCaseFailed) {
        this.useCaseFailed = useCaseFailed;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
