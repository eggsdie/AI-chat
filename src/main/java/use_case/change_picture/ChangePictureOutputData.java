package use_case.change_picture;

public class ChangePictureOutputData {
    private final String username;

    private final boolean useCaseFailed;

    public ChangePictureOutputData(String username, boolean useCaseFailed) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
