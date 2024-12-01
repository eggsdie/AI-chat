package interface_adapter.add_friend;

import interface_adapter.ViewModel;

/**
 * The `ChatListViewModel` class serves as the view model for the chat list functionality.
 * It extends the `ViewModel` base class and manages the state for the chat list UI.
 */
public class ChatListViewModel extends ViewModel<ChatListState> {

    /**
     * Constructs a new `ChatListViewModel` with a default chat list state.
     * Initializes the view model name as "chat list".
     */
    public ChatListViewModel() {
        super("chat list");
        setState(new ChatListState());
    }

}
