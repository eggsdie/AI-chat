package view;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import interface_adapter.add_friend.AddFriendController;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;

public class LoginView extends JPanel implements PropertyChangeListener {
    private final String viewName = "log in";

    private final LoginViewModel loginViewModel;
    private final JTextField usernameInputField;
    private final JPasswordField passwordInputField;
    private final JLabel errorLabel; // Error message label
    private LoginController loginController;
    private AddFriendController addFriendController;

    private final JButton loginButton;
    private final JButton cancelButton;

    public LoginView(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
        loginViewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("Log In");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.BLACK);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Error Label
        errorLabel = new JLabel("");
        errorLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorLabel.setVisible(false); // Initially hidden

        usernameInputField = createTextField("Username");
        usernameInputField.setAlignmentX(Component.CENTER_ALIGNMENT);

        passwordInputField = createPasswordField("Password");
        passwordInputField.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginButton = createStyledButton("Log In", new Color(66, 133, 244), Color.WHITE);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(evt -> {
            errorLabel.setVisible(false); // Clear error message on new login attempt
            loginController.execute(
                    usernameInputField.getText(),
                    new String(passwordInputField.getPassword())
            );
        });

        cancelButton = createStyledButton("Cancel", Color.WHITE, Color.BLACK);
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton.addActionListener(evt -> loginController.switchToSignupView());

        add(Box.createVerticalStrut(10));
        add(title);
        add(Box.createVerticalStrut(10));
        add(errorLabel); // Add the error label below the title
        add(Box.createVerticalStrut(20));
        add(usernameInputField);
        add(Box.createVerticalStrut(15));
        add(passwordInputField);
        add(Box.createVerticalStrut(20));
        add(loginButton);
        add(Box.createVerticalStrut(10));
        add(cancelButton);
    }

    private JTextField createTextField(String placeholder) {
        JTextField textField = new JTextField(15);
        styleTextField(textField, placeholder);
        return textField;
    }

    private JPasswordField createPasswordField(String placeholder) {
        JPasswordField passwordField = new JPasswordField(15);
        stylePasswordField(passwordField, placeholder);
        return passwordField;
    }

    private void styleTextField(JTextField field, String placeholder) {
        field.setFont(new Font("Arial", Font.PLAIN, 16));
        field.setPreferredSize(new Dimension(300, 40));
        field.setMaximumSize(new Dimension(300, 40));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        field.setText(placeholder);
        field.setForeground(Color.GRAY);
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(placeholder);
                }
            }
        });
    }

    private void stylePasswordField(JPasswordField field, String placeholder) {
        field.setFont(new Font("Arial", Font.PLAIN, 16));
        field.setPreferredSize(new Dimension(300, 40));
        field.setMaximumSize(new Dimension(300, 40));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        field.setEchoChar((char) 0); // Show text
        field.setText(placeholder);
        field.setForeground(Color.GRAY);

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                String currentText = new String(field.getPassword());
                if (currentText.equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                    field.setEchoChar('\u2022'); // Unicode bullet character
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (field.getPassword().length == 0) {
                    field.setEchoChar((char) 0); // Show text
                    field.setForeground(Color.GRAY);
                    field.setText(placeholder);
                }
            }
        });
    }

    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bgColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.setColor(fgColor);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - fm.getDescent();
                g2.drawString(getText(), x, y);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
            }
        };

        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(300, 40));
        button.setMaximumSize(new Dimension(300, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LoginState state = (LoginState) evt.getNewValue();
        usernameInputField.setText(state.getUsername());
        if (state.getPassword() == null || state.getPassword().isEmpty()) {
            passwordInputField.setEchoChar((char) 0); // Show placeholder text
            passwordInputField.setText("Password");
            passwordInputField.setForeground(Color.GRAY);
        } else {
            passwordInputField.setEchoChar('\u2022'); // Unicode bullet character
            passwordInputField.setText(state.getPassword());
            passwordInputField.setForeground(Color.BLACK);
        }

        // Update error message display
        String errorMessage = state.getLoginError();
        if (errorMessage != null && !errorMessage.isEmpty()) {
            // Assume there's a JLabel to display error messages
            errorLabel.setText(errorMessage);
            errorLabel.setForeground(Color.RED);
        } else {
            errorLabel.setText(""); // Clear error message if none
        }
    }

    public void resetFields() {
        usernameInputField.setText("Username");
        usernameInputField.setForeground(Color.GRAY);

        passwordInputField.setEchoChar((char) 0); // Show placeholder text
        passwordInputField.setText("Password");
        passwordInputField.setForeground(Color.GRAY);
        errorLabel.setText("");
        errorLabel.setVisible(false); // Hide error label
    }

    public String getViewName() {
        return viewName;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public void setChatListController(AddFriendController addFriendController) {
        this.addFriendController = addFriendController;
    }
}
