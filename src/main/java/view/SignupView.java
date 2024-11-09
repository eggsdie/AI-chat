package view;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

/**
 * The View for the Signup Use Case.
 */
public class SignupView extends JPanel implements PropertyChangeListener {
    private final String viewName = "sign up";

    private final SignupViewModel signupViewModel;
    private final JTextField nameInputField = new JTextField(15);
    private final JTextField emailInputField = new JTextField(15);
    private final JPasswordField passwordInputField = new JPasswordField(15);
    private SignupController signupController;

    private final JButton signUpButton;
    private final JButton googleSignUpButton;
    private final JButton toLoginButton;

    public SignupView(SignupViewModel signupViewModel) {
        this.signupViewModel = signupViewModel;
        signupViewModel.addPropertyChangeListener(this);

        // Title label
        JLabel title = new JLabel(SignupViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Input fields with labels
        LabelTextPanel namePanel = new LabelTextPanel(new JLabel("Name"), nameInputField);
        LabelTextPanel emailPanel = new LabelTextPanel(new JLabel("Email"), emailInputField);
        LabelTextPanel passwordPanel = new LabelTextPanel(new JLabel("Password"), passwordInputField);

        // Buttons
        signUpButton = new JButton(SignupViewModel.SIGNUP_BUTTON_LABEL);
        googleSignUpButton = new JButton("Sign up with Google");
        toLoginButton = new JButton(SignupViewModel.TO_LOGIN_BUTTON_LABEL);

        // Button actions
        signUpButton.addActionListener(evt -> {
            signupController.execute(
                    nameInputField.getText(),
                    emailInputField.getText(),
                    new String(passwordInputField.getPassword())
            );
        });

        googleSignUpButton.addActionListener(evt ->
                JOptionPane.showMessageDialog(this, "Google Sign Up not implemented.")
        );

        toLoginButton.addActionListener(evt -> signupController.switchToLoginView());

        // Layout setup
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(signUpButton);
        buttonsPanel.add(googleSignUpButton);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(namePanel);
        this.add(emailPanel);
        this.add(passwordPanel);
        this.add(buttonsPanel);
        this.add(toLoginButton);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SignupState state = (SignupState) evt.getNewValue();

        // Display any error messages
        if (state.getNameError() != null) {
            JOptionPane.showMessageDialog(this, state.getNameError());
        }
        if (state.getEmailError() != null) {
            JOptionPane.showMessageDialog(this, state.getEmailError());
        }
        if (state.getPasswordError() != null) {
            JOptionPane.showMessageDialog(this, state.getPasswordError());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setSignupController(SignupController controller) {
        this.signupController = controller;
    }
}
