package interface_adapter.friend_search;

import interface_adapter.ViewManagerModel;
import interface_adapter.add_friend.AddFriendViewModel;
import use_case.friend_search.FriendSearchOutputBoundary;

public class FriendSearchPresenter implements FriendSearchOutputBoundary {

    private final AddFriendViewModel addFriendViewModel;
    private final ViewManagerModel viewManagerModel;

    public FriendSearchPresenter(ViewManagerModel viewManagerModel,
                                 AddFriendViewModel addFriendViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.addFriendViewModel = addFriendViewModel;
    }

    public void switchToAddFriendView() {
        viewManagerModel.setState(addFriendViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
