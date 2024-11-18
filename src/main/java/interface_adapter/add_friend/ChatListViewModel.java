package interface_adapter.add_friend;

import interface_adapter.ViewModel;

public class ChatListViewModel extends ViewModel<ChatListState> {

    public ChatListViewModel() {
        super("chat list");
        setState(new ChatListState());
    }

}
