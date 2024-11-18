package interface_adapter.chat_list;

import entity.ChatEntry;
import interface_adapter.ViewManagerModel;
import use_case.chat_list.ChatListOutputBoundary;
import use_case.chat_list.ChatListOutputData;

import java.util.List;

public class ChatListPresenter implements ChatListOutputBoundary {

    private final ChatListViewModel chatListViewModel;
    // private final InChatViewModel inChatViewModel;
    private final ViewManagerModel viewManagerModel;

    public ChatListPresenter(ViewManagerModel viewManagerModel,
                             ChatListViewModel chatListViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.chatListViewModel = chatListViewModel;
        // this.inChatViewModel = inChatViewModel;
    }

    @Override
    public void prepareSuccessView(ChatListOutputData outputData) {
        // On success, switch to the in chat view.
//        final InChatState inChatState = inChatViewModel.getState();
//        this.inChatViewModel.setState(inChatState);
//        inChatViewModel.firePropertyChanged();
//        viewManagerModel.setState(inChatViewModel.getViewName());
//        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final ChatListState chatListState = chatListViewModel.getState();
        chatListState.setAddFriendError(errorMessage);
        chatListViewModel.firePropertyChanged();
    }

    @Override
    public void presentChatList(List<ChatEntry> chatList) {
        System.out.println("Chat List:");
        for (ChatEntry chat : chatList) {
            System.out.println(chat.getName() + " - " + chat.getLastMessagePreview()
                   + " at " + chat.getLastMessageTime());
        }
    }

    @Override
    public void presentError(String errorMessage) {
        System.out.println("Error: " + errorMessage);
    }

}
