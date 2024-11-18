package use_case.chat_list;

public class ChatListOutputData {

    private final boolean useCaseFailed;

    public ChatListOutputData(boolean useCaseFailed) {
        this.useCaseFailed = useCaseFailed;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
