package view;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

/**
 * The View for the Signup Use Case.
 */
public class SignupView extends JPanel implements PropertyChangeListener {
    private final String viewName = "sign up";

    private final SignupViewModel signupViewModel;
    private final JTextField nameInputField;
    private final JTextField emailInputField;
    private final JPasswordField passwordInputField;
    private SignupController signupController;

    private final JButton signUpButton;
    private final JButton googleSignUpButton;
    private final JLabel loginLink;

    public SignupView(SignupViewModel signupViewModel) {
        this.signupViewModel = signupViewModel;
        signupViewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("Sign up");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.BLACK);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        nameInputField = createTextField("Username");
        nameInputField.setAlignmentX(Component.CENTER_ALIGNMENT);

        emailInputField = createTextField("Email");
        emailInputField.setAlignmentX(Component.CENTER_ALIGNMENT);

        passwordInputField = createPasswordField("Password");
        passwordInputField.setAlignmentX(Component.CENTER_ALIGNMENT);

        signUpButton = createStyledButton("Sign Up", new Color(66, 133, 244), Color.WHITE);
        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signUpButton.addActionListener(evt -> {
            signupController.execute(
                    nameInputField.getText(),
                    emailInputField.getText(),
                    new String(passwordInputField.getPassword())
            );
        });

        loginLink = new JLabel("Already have an account? Log In");
        loginLink.setFont(new Font("Arial", Font.PLAIN, 12));
        loginLink.setForeground(new Color(66, 133, 244));
        loginLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                signupController.switchToLoginView();
            }
        });

        googleSignUpButton = createStyledButton("Sign up with Google", Color.WHITE, Color.BLACK);
        googleSignUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        googleSignUpButton.addActionListener(evt ->
                JOptionPane.showMessageDialog(this, "Google Sign Up not implemented.")
        );

        add(Box.createVerticalStrut(10));
        add(title);
        add(Box.createVerticalStrut(20));
        add(nameInputField);
        add(Box.createVerticalStrut(15));
        add(emailInputField);
        add(Box.createVerticalStrut(15));
        add(passwordInputField);
        add(Box.createVerticalStrut(20));
        add(signUpButton);
        add(Box.createVerticalStrut(10));
        add(loginLink);
        add(Box.createVerticalStrut(20));
        add(createDividerLabel("or"));
        add(Box.createVerticalStrut(10));
        add(googleSignUpButton);
    }

    // Helper method to create and style JTextField with a placeholder hint
    private JTextField createTextField(String placeholder) {
        JTextField textField = new JTextField(15);
        styleTextField(textField, placeholder);
        return textField;
    }

    // Helper method to create and style JPasswordField with a placeholder hint
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
                BorderFactory.createEmptyBorder(10, 10, 10, 10) // Add padding inside text field
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

        field.setEchoChar((char) 0); 
        field.setText(placeholder);
        field.setForeground(Color.GRAY);

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                String currentText = new String(field.getPassword());
                if (currentText.equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                    field.setEchoChar('\u2022'); 
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (field.getPassword().length == 0) {
                    field.setEchoChar((char) 0); 
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
                // No border needed
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

    private JLabel createDividerLabel(String text) {
        JLabel divider = new JLabel(text);
        divider.setFont(new Font("Arial", Font.PLAIN, 12));
        divider.setForeground(Color.GRAY);
        divider.setAlignmentX(Component.CENTER_ALIGNMENT);
        return divider;
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
