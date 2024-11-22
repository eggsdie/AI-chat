package interface_adapter.enter_chat;

import interface_adapter.ViewModel;

public class InChatViewModel extends ViewModel<InChatState> {

    public InChatViewModel() {
        super("in chat");
        setState(new InChatState());
    }

}
