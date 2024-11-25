package view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import interface_adapter.chat_list.ChatListController;
import interface_adapter.logout.LogoutController;
import interface_adapter.settings.SettingsController;
import interface_adapter.settings.SettingsState;
import interface_adapter.settings.SettingsViewModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;

public class SettingsView extends JPanel implements PropertyChangeListener {
    private final String viewName = "settings";
    private final SettingsViewModel settingsViewModel;
    
    private final JLabel profilePicture;
    private final JLabel usernameLabel;
    private final JLabel emailLabel;
    private final JButton uploadButton;
    private final JButton changePasswordButton;
    private final JButton logoutButton;
    private final JButton chatListButton;
    private final JButton settingsButton;
    private final JTextField currentPasswordField;
    private final JTextField newPasswordField;
    private final JTextField retypeNewPasswordField;
    private ChatListController chatListController;
    private LogoutController logoutController;
    private SettingsController settingsController;

    public SettingsView(SettingsViewModel settingsViewModel) {
        this.settingsViewModel = settingsViewModel;
        this.settingsViewModel.addPropertyChangeListener(this);

        // Main layout
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top panel: Profile info
        final JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.X_AXIS));
        profilePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Profile picture area for displaying an image
        profilePicture = new JLabel();
        profilePicture.setPreferredSize(new Dimension(60, 60));
        profilePicture.setOpaque(true);
        profilePicture.setBackground(Color.GRAY);
        profilePicture.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        profilePanel.add(profilePicture);

        // User info (Username and email)
        final JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.Y_AXIS));
        usernameLabel = new JLabel();
        emailLabel = new JLabel();
        userInfoPanel.add(usernameLabel);
        userInfoPanel.add(emailLabel);
        profilePanel.add(Box.createHorizontalStrut(10));
        profilePanel.add(userInfoPanel);

        // Upload button
        uploadButton = new JButton("Upload");
        profilePanel.add(Box.createHorizontalGlue());
        profilePanel.add(uploadButton);

        this.add(profilePanel, BorderLayout.NORTH);

        // Center panel: Password management
        final JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new GridLayout(4, 2, 10, 10));
        passwordPanel.setBorder(BorderFactory.createTitledBorder("Change Password"));

        // Fields for password management
        passwordPanel.add(new JLabel("Current Password:"));
        currentPasswordField = new JTextField();
        passwordPanel.add(currentPasswordField);

        passwordPanel.add(new JLabel("New Password:"));
        newPasswordField = new JTextField();
        passwordPanel.add(newPasswordField);

        passwordPanel.add(new JLabel("Retype New Password:"));
        retypeNewPasswordField = new JTextField();
        passwordPanel.add(retypeNewPasswordField);

        // Change Password Button
        changePasswordButton = new JButton("Change Password");
        passwordPanel.add(changePasswordButton);

        this.add(passwordPanel, BorderLayout.CENTER);

        // Bottom panel: Logout and navigation
        final JPanel bottomPanel = new JPanel(new BorderLayout());

        logoutButton = new JButton("Logout");
        final JPanel logoutPanel = new JPanel();
        logoutPanel.add(logoutButton);

        // Create the panel
        final JPanel panel = new JPanel(new BorderLayout());

        // Create the buttons
        chatListButton = new JButton("Chat List");
        settingsButton = new JButton("Settings");

        // Add the buttons to the panel
        panel.add(chatListButton, BorderLayout.WEST);
        panel.add(settingsButton, BorderLayout.EAST);

        bottomPanel.add(logoutPanel, BorderLayout.CENTER);
        bottomPanel.add(panel, BorderLayout.SOUTH);

        this.add(bottomPanel, BorderLayout.SOUTH);

        // Action Listeners
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uploadProfilePicture();
            }
        });

        // How to save new password and get current password
        changePasswordButton.addActionListener(evt -> {
            // Get the input from the text fields
            final SettingsState currentState = settingsViewModel.getState();
            final String enteredCurrentPassword = currentPasswordField.getText();
            final String enteredNewPassword = newPasswordField.getText();
            final String retypeNewPassword = retypeNewPasswordField.getText();

            // Validate current password
            if (!enteredCurrentPassword.equals(currentState.getPassword())) {
                JOptionPane.showMessageDialog(null, "Current password is incorrect!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate new password match
            if (!enteredNewPassword.equals(retypeNewPassword)) {
                JOptionPane.showMessageDialog(null, "New passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate that the new password is not empty
            if (enteredNewPassword.isEmpty()) {
                JOptionPane.showMessageDialog(null, "New password cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // If all validations pass, update the password
            // set user new password using enteredNewPassword
            JOptionPane.showMessageDialog(null, "Password successfully changed!", "Success", JOptionPane.INFORMATION_MESSAGE);
            currentState.setPassword(enteredNewPassword);
            
            // Clear the text fields
            currentPasswordField.setText("");
            newPasswordField.setText("");
            retypeNewPasswordField.setText("");
        });

        logoutButton.addActionListener(e -> {
            final SettingsState currentState = settingsViewModel.getState();
            logoutController.execute(currentState.getUsername());

        });

        chatListButton.addActionListener(evt -> {
            settingsController.switchToChatListView();
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SettingsState state = (SettingsState) evt.getNewValue();
        usernameLabel.setText(state.getUsername());
        emailLabel.setText(state.getEmail());
    }

    // How to save profile picture into user
    private void uploadProfilePicture() {
        // Use a JFileChooser to let the user select an image file
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg", "gif"));
        final int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            final File selectedFile = fileChooser.getSelectedFile();
            final ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());

            // Resize the image to fit the profile picture area
            final Image scaledImage = imageIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            profilePicture.setIcon(new ImageIcon(scaledImage));
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }

    public void setSettingsController(SettingsController settingsController) {
        this.settingsController = settingsController;
    }
}
