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
    }

    @Override
    public void prepareSuccessView(ChatListOutputData outputData) {
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
