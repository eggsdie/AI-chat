package interface_adapter.add_friend;

import interface_adapter.ViewManagerModel;
import interface_adapter.signup.SignupState;
import use_case.add_friend.AddFriendOutputBoundary;
import use_case.add_friend.AddFriendOutputData;

public class AddFriendPresenter implements AddFriendOutputBoundary {

    private final AddFriendViewModel addFriendViewModel;
    // private final InChatViewModel inChatViewModel;
    private final ViewManagerModel viewManagerModel;

    public AddFriendPresenter(ViewManagerModel viewManagerModel,
                           AddFriendViewModel addFriendViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.addFriendViewModel = addFriendViewModel;
        // this.inChatViewModel = inChatViewModel;
    }

    @Override
    public void prepareSuccessView(AddFriendOutputData outputData) {
        // On success, switch to the in chat view.
//        final InChatState inChatState = inChatViewModel.getState();
//        this.inChatViewModel.setState(inChatState);
//        inChatViewModel.firePropertyChanged();
//        viewManagerModel.setState(inChatViewModel.getViewName());
//        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final AddFriendState addFriendState = addFriendViewModel.getState();
        if ("User does not exist.".equals(errorMessage)) {
            addFriendState.setUserDoesNotExistError(errorMessage);
        }
        else if ("Friend already added.".equals(errorMessage)) {
            addFriendState.setFriendAlreadyAddedError(errorMessage);
        }
        addFriendViewModel.firePropertyChanged();
    }

    @Override
    public void switchToInChatView() {
        // viewManagerModel.setState(inChatViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
