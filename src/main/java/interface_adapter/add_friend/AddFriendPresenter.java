package interface_adapter.add_friend;

import interface_adapter.ViewManagerModel;
import use_case.add_friend.AddFriendOutputBoundary;
import use_case.add_friend.AddFriendOutputData;

public class AddFriendPresenter implements AddFriendOutputBoundary {

    private final ChatListViewModel chatListViewModel;
    // private final InChatViewModel inChatViewModel;
    private final ViewManagerModel viewManagerModel;

    public AddFriendPresenter(ViewManagerModel viewManagerModel,
                              ChatListViewModel chatListViewModel) {
        this.viewManagerModel = viewManagerModel;
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

//    @Override
//    public void presentChatList(List<ChatEntry> chatList) {
//        System.out.println("Chat List:");
//        for (ChatEntry chat : chatList) {
//            System.out.println(chat.getUser2() + " - " + chat.getLastMessagePreview()
//                   + " at " + chat.getLastMessageTime());
//        }
//    }

//    @Override
//    public void presentError(String errorMessage) {
//        System.out.println("Error: " + errorMessage);
//    }

}
