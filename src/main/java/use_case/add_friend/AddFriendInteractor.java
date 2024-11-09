package use_case.add_friend;

import entity.Chat;
import entity.Friend;

public class AddFriendInteractor implements AddFriendInputBoundary {

    private final AddFriendUserDataAccessInterface addFriendUserDataAccessInterface;
    private final AddFriendOutputBoundary friendPresenter;

    public AddFriendInteractor(AddFriendUserDataAccessInterface addFriendUserDataAccessInterface,
                               AddFriendOutputBoundary addFriendOutputBoundary) {
        this.addFriendUserDataAccessInterface = addFriendUserDataAccessInterface;
        this.friendPresenter = addFriendOutputBoundary;
    }

    public void execute(AddFriendInputData addFriendInputData) {
        if (!addFriendUserDataAccessInterface.userExists(addFriendInputData.getUser())) {
            friendPresenter.prepareFailView("User does not exist.");
        }
        else if (addFriendUserDataAccessInterface.friendExists(addFriendInputData.getUser())) {
            friendPresenter.prepareFailView("Friend already added.");
        }
        else {
            final Friend friend = new Friend(addFriendInputData.getUser());
            final Chat chat = new Chat(friend);
            addFriendUserDataAccessInterface.saveFriend(friend, chat);

            final AddFriendOutputData addChatOutputData = new AddFriendOutputData(false);
            friendPresenter.prepareSuccessView(addChatOutputData);
        }
    }
}
