package app;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.DemoRestfulApi;
// import data_access.InMemoryFriendRepository;
import data_access.InMemoryUserDataAccessObject;
import entity.CommonUserFactory;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.ChangePasswordPresenter;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.add_friend.AddFriendController;
import interface_adapter.add_friend.AddFriendPresenter;
import interface_adapter.add_friend.ChatListViewModel;
import interface_adapter.enter_chat.EnterChatController;
import interface_adapter.enter_chat.EnterChatPresenter;
import interface_adapter.enter_chat.InChatViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.send_message.SendMessageController;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.settings.SettingsController;
import interface_adapter.settings.SettingsPresenter;
import interface_adapter.settings.SettingsViewModel;
import use_case.add_friend.AddFriendInputBoundary;
import use_case.add_friend.AddFriendInteractor;
import use_case.add_friend.AddFriendOutputBoundary;
import use_case.change_password.ChangePasswordInputBoundary;
import use_case.change_password.ChangePasswordInteractor;
import use_case.change_password.ChangePasswordOutputBoundary;
import use_case.enter_chat.EnterChatInputBoundary;
import use_case.enter_chat.EnterChatInteractor;
import use_case.enter_chat.EnterChatOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.send_message.SendMessageInputBoundary;
import use_case.send_message.SendMessageInteracter;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import use_case.settings.SettingsInputBoundary;
import use_case.settings.SettingsInteractor;
import use_case.settings.SettingsOutputBoundary;

import view.LoggedInView;
import view.LoginView;
import view.SignupView;
import view.LandingView;
import view.*;

/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 */
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    private final UserFactory userFactory = new CommonUserFactory();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final DemoRestfulApi demoRestfulApi = new DemoRestfulApi();
    private final InMemoryUserDataAccessObject userDataAccessObject = new InMemoryUserDataAccessObject(demoRestfulApi,
            userFactory);
    // private final InMemoryFriendRepository friendRepository = new InMemoryFriendRepository(userDataAccessObject);

    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginViewModel loginViewModel;
    private LoggedInViewModel loggedInViewModel;
    private LoggedInView loggedInView;
    private LoginView loginView;
    private ChatListViewModel chatListViewModel;
    private ChatListView chatListView;
    private InChatViewModel inChatViewModel;
    private InChatView inChatView;

    private SettingsViewModel settingsViewModel;
    private SettingsView settingsView;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);

        viewManagerModel.addPropertyChangeListener(evt -> {
            String newState = (String) evt.getNewValue();
            cardLayout.show(cardPanel, newState);
        });
    }

    /**
     * Adds the Landing View to the application.
     * @return this builder
     */
    public AppBuilder addLandingView() {
        LandingView landingView = new LandingView(
                evt -> viewManagerModel.setState("log in"),  // Navigate to Login View
                evt -> viewManagerModel.setState("sign up")  // Navigate to Signup View
        );
        cardPanel.add(landingView, landingView.getViewName());
        return this;
    }

    /**
     * Adds the Signup View to the application.
     * @return this builder
     */
    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    /**
     * Adds the Login View to the application.
     * @return this builder
     */
    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    /**
     * Adds the LoggedIn View to the application.
     * @return this builder
     */
    public AppBuilder addLoggedInView() {
        loggedInViewModel = new LoggedInViewModel();
        loggedInView = new LoggedInView(loggedInViewModel);
        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

    /**
     * Adds the ChatList View to the application.
     * @return this builder
     */
    public AppBuilder addChatListView() {
        chatListViewModel = new ChatListViewModel();
        chatListView = new ChatListView(chatListViewModel);
        cardPanel.add(chatListView, chatListView.getViewName());
        return this;
    }

    /**
     * Adds the InChat View to the application.
     * @return this builder
     */
    public AppBuilder addInChatView() {
        inChatViewModel = new InChatViewModel();
        inChatView = new InChatView(inChatViewModel);
        cardPanel.add(inChatView, inChatView.getViewName());
        return this;
    }

    public AppBuilder addSettingsView() {
        settingsViewModel = new SettingsViewModel();
        settingsView = new SettingsView(settingsViewModel);
        cardPanel.add(settingsView, settingsView.getViewName());
        return this;
    }


    /**
     * Adds the Signup Use Case to the application.
     * @return this builder
     */
    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel, signupViewModel, loginViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(userDataAccessObject, signupOutputBoundary, userFactory);

        final SignupController signupController = new SignupController(userSignupInteractor);
        signupView.setSignupController(signupController);
        return this;
    }

    /**
     * Adds the Login Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel, chatListViewModel,
                loginViewModel);
        final AddFriendOutputBoundary addFriendOutputBoundary = new AddFriendPresenter(viewManagerModel,
                chatListViewModel);

        final LoginInputBoundary loginInteractor = new LoginInteractor(userDataAccessObject, loginOutputBoundary);
        final AddFriendInputBoundary chatListInteractor =
                new AddFriendInteractor(userDataAccessObject, addFriendOutputBoundary);

        final LoginController loginController = new LoginController(loginInteractor, viewManagerModel);
        loginView.setLoginController(loginController);
        final AddFriendController addFriendController = new AddFriendController(chatListInteractor);
        loginView.setChatListController(addFriendController);
        return this;
    }

    /**
     * Adds the Change Password Use Case to the application.
     * @return this builder
     */
    public AppBuilder addChangePasswordUseCase() {
        final ChangePasswordOutputBoundary changePasswordOutputBoundary = new ChangePasswordPresenter(loggedInViewModel);
        final ChangePasswordInputBoundary changePasswordInteractor = new ChangePasswordInteractor(userDataAccessObject, changePasswordOutputBoundary, userFactory);

        final ChangePasswordController changePasswordController = new ChangePasswordController(changePasswordInteractor);
        loggedInView.setChangePasswordController(changePasswordController);
        return this;
    }

    /**
     * Adds the Logout Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLogoutUseCase() {
        final LogoutOutputBoundary logoutOutputBoundary = new LogoutPresenter(viewManagerModel, loggedInViewModel, signupViewModel);
        final LogoutInputBoundary logoutInteractor = new LogoutInteractor(userDataAccessObject, logoutOutputBoundary);

        final LogoutController logoutController = new LogoutController(logoutInteractor, viewManagerModel);
        loggedInView.setLogoutController(logoutController);
        // Connect controller to the view
        settingsView.setLogoutController(logoutController);
        // Ensure the Settings view can also log out
        return this;
    }

    /**
     * Creates the JFrame for the application and initially sets the LandingView to be displayed.
     * Adds the AddFriend Use Case to the application.
     * @return this builder
     */
    public AppBuilder addChatListUseCase() {
        final AddFriendOutputBoundary addFriendOutputBoundary = new AddFriendPresenter(viewManagerModel,
                chatListViewModel);
        final EnterChatOutputBoundary enterChatOutputBoundary =
                new EnterChatPresenter(viewManagerModel, inChatViewModel, chatListViewModel);
        final SettingsOutputBoundary settingsOutputBoundary =
                new SettingsPresenter(viewManagerModel, settingsViewModel, chatListViewModel);

        final AddFriendInputBoundary chatListInteractor =
                new AddFriendInteractor(userDataAccessObject, addFriendOutputBoundary);
        final EnterChatInputBoundary enterChatInteractor = new EnterChatInteractor(userDataAccessObject,
                enterChatOutputBoundary);
        final SettingsInputBoundary settingsInteractor = new SettingsInteractor(settingsOutputBoundary);

        final AddFriendController addFriendController = new AddFriendController(chatListInteractor);
        chatListView.setChatListController(addFriendController);
        final EnterChatController enterChatController = new EnterChatController(enterChatInteractor);
        chatListView.setEnterChatController(enterChatController);
        final SettingsController settingsController = new SettingsController(settingsInteractor);
        chatListView.setSettingsController(settingsController);
        return this;
    }

    /**
     * Adds the EnterChat Use Case to the application.
     * @return this builder
     */
    public AppBuilder addEnterChatUseCase() {
        final EnterChatOutputBoundary enterChatOutputBoundary =
                new EnterChatPresenter(viewManagerModel, inChatViewModel, chatListViewModel);

        final EnterChatInputBoundary enterChatInteractor = new EnterChatInteractor(userDataAccessObject,
                enterChatOutputBoundary);

        final EnterChatController enterChatController = new EnterChatController(enterChatInteractor);
        inChatView.setEnterChatController(enterChatController);
        return this;
    }

    public AppBuilder addSettingsUseCase() {
        final SettingsOutputBoundary settingsOutputBoundary = new SettingsPresenter(viewManagerModel, settingsViewModel,
                chatListViewModel);
        final SettingsInputBoundary settingsInteractor = new SettingsInteractor(settingsOutputBoundary);

        final SettingsController settingsController = new SettingsController(settingsInteractor);
        settingsView.setSettingsController(settingsController);
        return this;
    }

    public AppBuilder addSendMessageUseCase() {
        final SendMessageInputBoundary sendMessageInteractor = new SendMessageInteracter(userDataAccessObject);

        final SendMessageController sendMessageController = new SendMessageController(sendMessageInteractor);
        inChatView.setSendMessageController(sendMessageController);
        return this;
    }

    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("AI CHAT");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.add(cardPanel);

        viewManagerModel.setState("landing");
        viewManagerModel.firePropertyChanged();

        return application;
    }

}