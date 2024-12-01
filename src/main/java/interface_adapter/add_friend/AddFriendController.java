package interface_adapter.add_friend;

import entity.User;
import use_case.add_friend.AddFriendInputBoundary;
import use_case.add_friend.AddFriendInputData;

public class AddFriendController {

    private final AddFriendInputBoundary chatListUseCaseInteractor;

    public AddFriendController(AddFriendInputBoundary chatListUseCaseInteractor) {
        this.chatListUseCaseInteractor = chatListUseCaseInteractor;
    }

    public void execute(User currentUser, String otherUser, String messagePreview) {
        final AddFriendInputData addFriendInputData = new AddFriendInputData(currentUser, otherUser);

        chatListUseCaseInteractor.execute(addFriendInputData, messagePreview);
    }

}
