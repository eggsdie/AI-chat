package interface_adapter.friend_search;

import use_case.friend_search.FriendSearchInputBoundary;

public class FriendSearchController {

    private final FriendSearchInputBoundary friendSearchUseCaseInteractor;

    public FriendSearchController(FriendSearchInputBoundary friendSearchUseCaseInteractor) {
        this.friendSearchUseCaseInteractor = friendSearchUseCaseInteractor;
    }

    public void switchToAddFriendView() {
        friendSearchUseCaseInteractor.switchToAddFriendView();
    }

}
