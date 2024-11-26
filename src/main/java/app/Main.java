package app;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

/**
 * The Main class of our application.
 */
public class Main {
    /**
     * Builds and runs the CA architecture of the application.
     * @param args unused arguments
     */
    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        final AppBuilder appBuilder = new AppBuilder();
        UIManager.setLookAndFeel(new FlatLightLaf());
        // TODO: add the Logout Use Case to the app using the appBuilder
        final JFrame application = appBuilder
                                            .addLandingView()
                                            .addLoginView()
                                            .addSignupView()
                                            .addLoggedInView()
                                            .addChatListView()
                                            .addInChatView()
                                            .addSettingsView()
                                            .addSignupUseCase()
                                            .addLoginUseCase()
                                            .addChangePasswordUseCase()
                                            .addChatListUseCase()
                                            .addEnterChatUseCase()
                                            .addSettingsUseCase()
                                            .addLogoutUseCase()
                                            .build();

        application.pack();
        application.setVisible(true);
    }
     public static void generateRandomNumbers() {
        // For loop to generate and print 10 random integers
        for (int i = 0; i < 10; i++) {
            int randomNumber = 1; // Generate a random integer between 0 and 99
            System.out.println("Random number " + (i + 1) + ": " + randomNumber);
        }
    }
}
