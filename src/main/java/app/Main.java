package app;

import javax.swing.*;

import com.formdev.flatlaf.FlatLightLaf;

/**
 * The Main class of our application.
 */
public class Main {
    /**
     * Builds and runs the CA architecture of the application.
     * @param args unused arguments
     * @throws UnsupportedLookAndFeelException unsupportedLookAndFeelException
     */
    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        final AppBuilder appBuilder = new AppBuilder();
        UIManager.setLookAndFeel(new FlatLightLaf());
        final JFrame application = appBuilder
                                            .addLandingView()
                                            .addLoginView()
                                            .addSignupView()
                                            .addChatListView()
                                            .addInChatView()
                                            .addSettingsView()
                                            .addSignupUseCase()
                                            .addLoginUseCase()
                                            .addChangePasswordUseCase()
                                            .addAddFriendUseCase()
                                            .addEnterChatUseCase()
                                            .addSettingsUseCase()
                                            .addSendMessageUseCase()
                                            .addLogoutUseCase()
                                            .build();

        application.pack();
        application.setVisible(true);

    }
}
