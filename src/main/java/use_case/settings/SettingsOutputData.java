package use_case.settings;

public class SettingsOutputData {
    private final String username;
    private final String email;

    private final String password;
    private final String profilePicture;
    private final boolean useCaseFailed;

    public SettingsOutputData(String username, String email, String password, String profilePicture, boolean useCaseFailed) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.profilePicture = profilePicture;
        this.useCaseFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

    public String getPicture() {
        return profilePicture;
    }
}
