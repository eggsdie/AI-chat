package use_case.settings;

public class SettingsOutputData {
    private final String username;
    private final String email;

    private final String password;
    private final boolean useCaseFailed;

    public SettingsOutputData(String username, String email, String password, boolean useCaseFailed) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.useCaseFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {return password;}

}
