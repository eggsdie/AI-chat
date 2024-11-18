package use_case.ChatList;

public class ChatListOutputData {

    private final boolean useCaseFailed;

    public ChatListOutputData(boolean useCaseFailed) {
        this.useCaseFailed = useCaseFailed;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
