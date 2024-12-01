package interface_adapter.add_friend;

import use_case.add_friend.AddFriendOutputBoundary;
import use_case.add_friend.AddFriendOutputData;

public class AddFriendPresenter implements AddFriendOutputBoundary {

    private final ChatListViewModel chatListViewModel;

    public AddFriendPresenter(ChatListViewModel chatListViewModel) {
        this.chatListViewModel = chatListViewModel;
    }

    @Override
    public void prepareSuccessView(AddFriendOutputData outputData) {
        final ChatListState chatListState = chatListViewModel.getState();
        chatListState.setChatList(outputData.getChatList());
        chatListState.setActiveUser(outputData.getActiveUser());
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final ChatListState chatListState = chatListViewModel.getState();
        chatListState.setAddFriendError(errorMessage);
        chatListViewModel.firePropertyChanged();
    }

}
