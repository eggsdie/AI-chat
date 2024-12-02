package use_case.change_picture;

public class ChangePictureInputData {

    private final String profilePicture;
    private final String username;

    public ChangePictureInputData(String profilePicture, String username) {
        this.profilePicture = profilePicture;
        this.username = username;
    }

    String getProfilePicture() {
        return profilePicture;
    }

    String getUsername() {
        return username;
    }
}
