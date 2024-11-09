package use_case.add_friend;

public class AddFriendOutputData {

    private final boolean useCaseFailed;

    public AddFriendOutputData(boolean useCaseFailed) {
        this.useCaseFailed = useCaseFailed;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
