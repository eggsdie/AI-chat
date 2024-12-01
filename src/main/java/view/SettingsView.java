package view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import interface_adapter.add_friend.AddFriendController;
import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.logout.LogoutController;
import interface_adapter.settings.SettingsController;
import interface_adapter.settings.SettingsState;
import interface_adapter.settings.SettingsViewModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.*;

/**
 * A professional-looking and polished Settings View with a structured layout.
 */
public class SettingsView extends JPanel implements PropertyChangeListener {

    // View name for state management
    private final String viewName = "settings";

    // ViewModel for state management
    private final SettingsViewModel settingsViewModel;

    // Components for user profile
    private JLabel profilePicture;
    private JLabel usernameLabel;
    private JLabel emailLabel;
    private JButton uploadButton;

    // Components for password management
    private JTextField currentPasswordField;
    private JTextField newPasswordField;
    private JTextField retypeNewPasswordField;
    private JButton changePasswordButton;

    // Navigation and logout buttons
    private JButton logoutButton;
    private JButton chatListButton;
    private JButton settingsButton;

    // Controllers for interaction
    private AddFriendController addFriendController;
    private LogoutController logoutController;
    private SettingsController settingsController;
    private ChangePasswordController changePasswordController;

    /**
     * Constructor for the Settings View.
     *
     * @param settingsViewModel The ViewModel that manages the settings state.
     */
    public SettingsView(SettingsViewModel settingsViewModel) {
        // Assign the ViewModel
        this.settingsViewModel = settingsViewModel;

        // Add a property change listener to the ViewModel
        this.settingsViewModel.addPropertyChangeListener(this);

        // Set the layout manager
        setLayout(new BorderLayout());

        // Set padding around the edges of the panel
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add the header panel
        add(createHeaderPanel(), BorderLayout.NORTH);

        // Add the profile information panel
        add(createProfileInfoPanel(), BorderLayout.CENTER);

        // Add the bottom navigation panel
        add(createBottomPanel(), BorderLayout.SOUTH);
    }

    /**
     * Creates the header panel containing the page title.
     */
    private JPanel createHeaderPanel() {
        // Create the header panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Create a label for the title
        JLabel titleLabel = new JLabel("Settings");

        // Set font properties for the title
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Add the title label to the header panel
        headerPanel.add(titleLabel);

        return headerPanel; // Return the header panel
    }

    /**
     * Creates the panel for displaying user profile information.
     */
    private JPanel createProfileInfoPanel() {
        // Create the main profile info panel
        JPanel profileInfoPanel = new JPanel();

        // Set the layout manager to BorderLayout
        profileInfoPanel.setLayout(new BorderLayout(20, 20));

        // Add a border with a title
        profileInfoPanel.setBorder(BorderFactory.createTitledBorder("Profile Information"));

        // Create the profile picture label
        profilePicture = new JLabel();

        // Set the preferred size for the profile picture
        profilePicture.setPreferredSize(new Dimension(80, 80));

        // Make the profile picture background opaque
        profilePicture.setOpaque(true);

        // Set a light gray background for the profile picture
        profilePicture.setBackground(Color.LIGHT_GRAY);

        // Center align the profile picture
        profilePicture.setHorizontalAlignment(SwingConstants.CENTER);

        // Add a gray border around the profile picture
        profilePicture.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        // Create the upload button
        uploadButton = new JButton("Upload Picture");

        // Add an action listener to the upload button
        uploadButton.addActionListener(this::uploadProfilePicture);

        // Create a panel to hold the profile picture and upload button
        JPanel profilePicturePanel = new JPanel();

        // Set the layout manager to BorderLayout
        profilePicturePanel.setLayout(new BorderLayout());

        // Add the profile picture to the center of the panel
        profilePicturePanel.add(profilePicture, BorderLayout.CENTER);

        // Add the upload button to the bottom of the panel
        profilePicturePanel.add(uploadButton, BorderLayout.SOUTH);

        // Create the username label
        usernameLabel = createStyledLabel("");

        // Create the email label
        emailLabel = createStyledLabel("");

        // Create a panel for user information
        JPanel userInfoPanel = new JPanel();

        // Set the layout manager to BoxLayout
        userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.Y_AXIS));

        // Add the username label to the user info panel
        userInfoPanel.add(createStyledLabel("Username:"));
        userInfoPanel.add(usernameLabel);

        // Add some vertical space
        userInfoPanel.add(Box.createVerticalStrut(10));

        // Add the email label to the user info panel
        userInfoPanel.add(createStyledLabel("Email:"));
        userInfoPanel.add(emailLabel);

        // Add the profile picture panel to the left of the main panel
        profileInfoPanel.add(profilePicturePanel, BorderLayout.WEST);

        // Add the user info panel to the center of the main panel
        profileInfoPanel.add(userInfoPanel, BorderLayout.CENTER);

        // Add the password management panel to the bottom of the main panel
        profileInfoPanel.add(createPasswordPanel(), BorderLayout.SOUTH);

        return profileInfoPanel; // Return the profile info panel
    }

    /**
     * Creates the panel for managing user passwords.
     */
    private JPanel createPasswordPanel() {
        // Create the password panel
        JPanel passwordPanel = new JPanel();

        // Set the layout manager to GridLayout
        passwordPanel.setLayout(new GridLayout(4, 2, 15, 15));

        // Add a border with a title
        passwordPanel.setBorder(BorderFactory.createTitledBorder("Change Password"));

        // Add the current password label and field
        passwordPanel.add(createStyledLabel("Current Password:"));
        currentPasswordField = createTextField();
        passwordPanel.add(currentPasswordField);

        // Add the new password label and field
        passwordPanel.add(createStyledLabel("New Password:"));
        newPasswordField = createTextField();
        passwordPanel.add(newPasswordField);

        // Add the retype new password label and field
        passwordPanel.add(createStyledLabel("Retype New Password:"));
        retypeNewPasswordField = createTextField();
        passwordPanel.add(retypeNewPasswordField);

        // Create the change password button
        changePasswordButton = createStyledButton("Change Password");

        // Add an action listener to the change password button
        changePasswordButton.addActionListener(this::changePassword);

        // Add the change password button to the password panel
        passwordPanel.add(changePasswordButton);

        return passwordPanel; // Return the password panel
    }

    /**
     * Creates the bottom panel with navigation and logout buttons.
     */
    private JPanel createBottomPanel() {
        // Create the bottom panel
        JPanel bottomPanel = new JPanel(new BorderLayout());

        // Create the logout button
        logoutButton = createStyledButton("Logout");

        // Add an action listener to the logout button
        logoutButton.addActionListener(e -> {
            final SettingsState state = settingsViewModel.getState();
            logoutController.execute(state.getUsername());
        });

        // Create the chat list button
        chatListButton = createStyledButton("Chat List");

        // Add an action listener to the chat list button
        chatListButton.addActionListener(e -> settingsController.switchToChatListView());

        // Create the settings button
        settingsButton = createStyledButton("Settings");

        // Create a panel for navigation buttons
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        // Add the chat list and settings buttons to the navigation panel
        navPanel.add(chatListButton);
        navPanel.add(settingsButton);

        // Add the logout button to the top of the bottom panel
        bottomPanel.add(logoutButton, BorderLayout.NORTH);

        // Add the navigation panel to the bottom of the bottom panel
        bottomPanel.add(navPanel, BorderLayout.SOUTH);

        return bottomPanel; // Return the bottom panel
    }

    /**
     * Handles the change password action.
     */
    private void changePassword(ActionEvent e) {
        final SettingsState state = settingsViewModel.getState();
        final String currentPassword = currentPasswordField.getText();
        final String newPassword = newPasswordField.getText();
        final String retypePassword = retypeNewPasswordField.getText();

        if (!currentPassword.equals(state.getPassword())) {
            JOptionPane.showMessageDialog(this, "Current password is incorrect!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if (!newPassword.equals(retypePassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if (newPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Password cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(this, "Password changed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            state.setPassword(newPassword);
            changePasswordController.execute(newPassword, state.getUsername());
        }

        currentPasswordField.setText("");
        newPasswordField.setText("");
        retypeNewPasswordField.setText("");
    }

    /**
     * Handles the profile picture upload action.
     */
    private void uploadProfilePicture(ActionEvent e) {
        // Predefined image paths
        String[] picturePaths = {
                "images/basketball.jpg",
                "images/cartoon_bears.jpg",
                "images/default.jpg",
                "images/flower.jpg",
                "images/puppy.jpg",
                "images/videogames.jpg"
        };

        // Create a panel to hold image buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 3, 10, 10)); // 2 rows, 3 columns for images

        // Variables to track selected image
        final String[] selectedImage = {null};

        // Add images to the panel as buttons
        for (String path : picturePaths) {
            final ImageIcon icon = new ImageIcon(path);
            final Image scaledImage = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            final JButton imageButton = new JButton(new ImageIcon(scaledImage));
            imageButton.setPreferredSize(new Dimension(80, 80));
            imageButton.setBorder(BorderFactory.createEmptyBorder());
            imageButton.setFocusPainted(false);

            // Set action listener for each button
            imageButton.addActionListener(ev -> selectedImage[0] = path);

            panel.add(imageButton);
        }

        // Show the dialog with the panel
        final int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Select Profile Picture",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        // If the user confirms selection
        if (result == JOptionPane.OK_OPTION && selectedImage[0] != null) {
            ImageIcon icon = new ImageIcon(selectedImage[0]);
            Image scaledImage = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            profilePicture.setIcon(new ImageIcon(scaledImage));
        }
    }


    /**
     * Updates the view based on changes in the ViewModel.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SettingsState state = (SettingsState) evt.getNewValue();
        usernameLabel.setText(state.getUsername());
        emailLabel.setText(state.getEmail());
    }

    /**
     * Utility method to create a styled label.
     */
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        return label;
    }

    /**
     * Utility method to create a styled text field.
     */
    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        return textField;
    }

    /**
     * Utility method to create a styled button.
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBackground(new Color(59, 89, 152));
        button.setForeground(Color.WHITE);
        return button;
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

    public void setChangePasswordController(ChangePasswordController changePasswordController) {
        this.changePasswordController = changePasswordController;
    }

}
