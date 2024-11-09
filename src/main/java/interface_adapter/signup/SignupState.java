package interface_adapter.signup;

/**
 * The state for the Signup View Model.
 */
public class SignupState {
    private String name = "";
    private String nameError;
    private String email = "";
    private String emailError;
    private String password = "";
    private String passwordError;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameError() {
        return nameError;
    }

    public void setNameError(String nameError) {
        this.nameError = nameError;
    }

    // Getter and Setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailError() {
        return emailError;
    }

    public void setEmailError(String emailError) {
        this.emailError = emailError;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    @Override
    public String toString() {
        return "SignupState{"
                + "name='" + name + '\''
                + ", email='" + email + '\''
                + ", password='" + password + '\''
                + '}';
    }
}
