package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.add_friend.ChatListState;
import interface_adapter.add_friend.ChatListViewModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

/**
 * The Presenter for the Login Use Case.
 */
public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final ChatListViewModel chatListViewModel;
    private final ViewManagerModel viewManagerModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          ChatListViewModel chatListViewModel,
                          LoginViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.chatListViewModel = chatListViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData response) {
        // Update ChatListState with successful login information
        final ChatListState chatListState = chatListViewModel.getState();
        chatListState.setCurrentUsername(response.getUsername());
        chatListState.setChatList(response.getChatEntries());
        chatListState.setActiveUser(response.getUser());
        chatListViewModel.setState(chatListState);
        chatListViewModel.firePropertyChanged();

        // Notify the view manager to switch to the chat list view
        viewManagerModel.setState(chatListViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        // Update the LoginState with the error message
        final LoginState loginState = loginViewModel.getState();
        loginState.setLoginError(error);
        loginViewModel.setState(loginState);
        loginViewModel.firePropertyChanged();
    }
}
