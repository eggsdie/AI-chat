package app;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.InMemoryFriendRepository;
import data_access.InMemoryUserDataAccessObject;
import entity.CommonUserFactory;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.chat_list.ChatListController;
import interface_adapter.chat_list.ChatListPresenter;
import interface_adapter.chat_list.ChatListViewModel;
import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.ChangePasswordPresenter;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.enter_chat.EnterChatController;
import interface_adapter.enter_chat.EnterChatPresenter;
import interface_adapter.enter_chat.InChatViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.chat_list.ChatListInputBoundary;
import use_case.chat_list.ChatListManager;
import use_case.chat_list.ChatListOutputBoundary;
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
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
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
    private final InMemoryUserDataAccessObject userDataAccessObject = new InMemoryUserDataAccessObject();
    private final InMemoryFriendRepository friendRepository = new InMemoryFriendRepository(userDataAccessObject);

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

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);

        viewManagerModel.addPropertyChangeListener(evt -> {
            String newState = (String) evt.getNewValue();
            cardLayout.show(cardPanel, newState);
        });
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
        final ChatListOutputBoundary chatListOutputBoundary = new ChatListPresenter(
                viewManagerModel, chatListViewModel);
        chatListView = new ChatListView(friendRepository, chatListOutputBoundary, chatListViewModel);
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
        final LoginInputBoundary loginInteractor = new LoginInteractor(userDataAccessObject, loginOutputBoundary);

        final LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
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
        final LogoutOutputBoundary logoutOutputBoundary = new LogoutPresenter(viewManagerModel, loggedInViewModel, signupViewModel); // Redirect to SignupViewModel
        final LogoutInputBoundary logoutInteractor = new LogoutInteractor(userDataAccessObject, logoutOutputBoundary);
        final LogoutController logoutController = new LogoutController(logoutInteractor);

        // Assign the logout controller to the loggedIn view
        loggedInView.setLogoutController(logoutController);
        return this;
    }

    /**
     * Adds the AddFriend Use Case to the application.
     * @return this builder
     */
    public AppBuilder addChatListUseCase() {
        final ChatListOutputBoundary chatListOutputBoundary = new ChatListPresenter(viewManagerModel,
                chatListViewModel);
        final EnterChatOutputBoundary enterChatOutputBoundary = new EnterChatPresenter(viewManagerModel,
                inChatViewModel);

        final ChatListInputBoundary chatListInteractor =
                new ChatListManager(friendRepository, chatListOutputBoundary);
        final EnterChatInputBoundary enterChatInteracter = new EnterChatInteractor(enterChatOutputBoundary);

        final ChatListController chatListController = new ChatListController(chatListInteractor);
        chatListView.setChatListController(chatListController);
        final EnterChatController enterChatController = new EnterChatController(enterChatInteracter);
        chatListView.setEnterChatController(enterChatController);
        return this;
    }

    /**
     * Adds the EnterChat Use Case to the application.
     * @return this builder
     */
    public AppBuilder addEnterChatUseCase() {
        final EnterChatOutputBoundary enterChatOutputBoundary = new EnterChatPresenter(viewManagerModel,
                inChatViewModel);

        final EnterChatInputBoundary enterChatInteractor =
                new EnterChatInteractor(enterChatOutputBoundary);

        final EnterChatController enterChatController = new EnterChatController(enterChatInteractor);
        inChatView.setEnterChatController(enterChatController);
        return this;
    }

    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("Login Example");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.add(cardPanel);

        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }

}
