package interface_adapter.friend_search;

import interface_adapter.ViewManagerModel;
import interface_adapter.add_friend.ChatListViewModel;
import use_case.friend_search.FriendSearchOutputBoundary;

public class FriendSearchPresenter implements FriendSearchOutputBoundary {

    private final ChatListViewModel chatListViewModel;
    private final ViewManagerModel viewManagerModel;

    public FriendSearchPresenter(ViewManagerModel viewManagerModel,
                                 ChatListViewModel chatListViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.chatListViewModel = chatListViewModel;
    }

    public void switchToAddFriendView() {
        viewManagerModel.setState(chatListViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
