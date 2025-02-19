package interface_adapter.enter_chat;

import entity.ChatEntry;
import interface_adapter.ViewManagerModel;
import interface_adapter.add_friend.ChatListState;
import interface_adapter.add_friend.ChatListViewModel;
import use_case.enter_chat.EnterChatOutputBoundary;
import use_case.enter_chat.EnterChatOutputData;

import java.util.ArrayList;

public class EnterChatPresenter implements EnterChatOutputBoundary {

    private final InChatViewModel inChatViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ChatListViewModel chatListViewModel;

    public EnterChatPresenter(ViewManagerModel viewManagerModel,
                              InChatViewModel inChatViewModel, ChatListViewModel chatListViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.inChatViewModel = inChatViewModel;
        this.chatListViewModel = chatListViewModel;
    }

    @Override
    public void prepareSuccessView(EnterChatOutputData outputData) {
        // On success, switch to the in chat view.
        final InChatState inChatState = inChatViewModel.getState();
        inChatState.setMessages(outputData.getMessages());
        inChatState.setSender(outputData.getSender());
        inChatState.setReceiver(outputData.getReceiver());
        this.inChatViewModel.setState(inChatState);
        this.inChatViewModel.firePropertyChanged();

        this.viewManagerModel.setState(inChatViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {

    }

    public void switchToChatListView(ArrayList<ChatEntry> chatList) {
        final ChatListState chatListState = chatListViewModel.getState();
        chatListState.setChatList(chatList);
        this.chatListViewModel.setState(chatListState);
        this.chatListViewModel.firePropertyChanged();

        viewManagerModel.setState(chatListViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

}
