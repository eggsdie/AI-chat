package interface_adapter.signup;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Signup View.
 */
public class SignupViewModel extends ViewModel<SignupState> {

    public static final String TITLE_LABEL = "Sign up for Chat Messenger App!";
    public static final String NAME_LABEL = "Name";
    public static final String EMAIL_LABEL = "Email";
    public static final String PASSWORD_LABEL = "Password";

    public static final String SIGNUP_BUTTON_LABEL = "Sign up";
    public static final String TO_LOGIN_BUTTON_LABEL = "Go to Login";

    public SignupViewModel() {
        super("sign up");
        setState(new SignupState());
    }
}
