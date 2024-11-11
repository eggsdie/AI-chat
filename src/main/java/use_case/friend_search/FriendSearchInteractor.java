package use_case.friend_search;

/**
 * The Logout Interactor.
 */
public class FriendSearchInteractor implements FriendSearchInputBoundary {

    private final FriendSearchOutputBoundary userPresenter;

    public FriendSearchInteractor(FriendSearchOutputBoundary userPresenter) {
        this.userPresenter = userPresenter;
    }

    @Override
    public void switchToAddFriendView() {
        userPresenter.switchToAddFriendView();
    }

}

