package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.chat_list.ChatListState;
import interface_adapter.chat_list.ChatListViewModel;
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
        // On success, switch to the logged in view.

        final ChatListState chatListState = chatListViewModel.getState();
        chatListState.setCurrentUsername(response.getUsername());
        chatListState.setChatList(response.getChatEntries());
        this.chatListViewModel.setState(chatListState);
        this.chatListViewModel.firePropertyChanged();

        this.viewManagerModel.setState(chatListViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final LoginState loginState = loginViewModel.getState();
        loginState.setLoginError(error);
        loginViewModel.firePropertyChanged();
    }
}
