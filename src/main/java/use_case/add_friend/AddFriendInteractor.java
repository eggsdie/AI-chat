package use_case.add_friend;

import entity.ChatEntry;

public class AddFriendInteractor implements AddFriendInputBoundary {
    private final AddFriendUserDataAccessInterface addFriendUserDataAccessInterface;
    private final AddFriendOutputBoundary userPresenter;

    public AddFriendInteractor(AddFriendUserDataAccessInterface addFriendUserDataAccessInterface,
                               AddFriendOutputBoundary addFriendOutputBoundary) {
        this.addFriendUserDataAccessInterface = addFriendUserDataAccessInterface;
        this.userPresenter = addFriendOutputBoundary;
    }

    @Override
    // Add a new chat entry
    public void execute(AddFriendInputData addFriendInputData, String messagePreview) {
        if (!addFriendUserDataAccessInterface.existsByName(addFriendInputData.getOtherUser())) {
            userPresenter.prepareFailView("User does not exist.");
        }
        else if (addFriendUserDataAccessInterface.chatWithYourself(addFriendInputData.getOtherUser())) {
            userPresenter.prepareFailView("Can't make a chat with yourself!");
        }
        else if (friendExists(addFriendInputData.getUsername(), addFriendInputData.getOtherUser())) {
            userPresenter.prepareFailView("Friend already added.");
        }
        else {
            addFriendUserDataAccessInterface.addMessage(addFriendInputData.getUsername(),
                    addFriendInputData.getOtherUser(),
                    messagePreview);
            final AddFriendOutputData addChatOutputData = new AddFriendOutputData(false,
                    addFriendUserDataAccessInterface.allChatsWithUser(addFriendInputData.getUsername()),
                    addFriendUserDataAccessInterface.get(addFriendInputData.getUsername()));
            userPresenter.prepareSuccessView(addChatOutputData);
        }
    }

    public boolean friendExists(String myUsername, String otherUser) {
        for (ChatEntry chatEntry : addFriendUserDataAccessInterface.allChatsWithUser(myUsername)) {
            if (chatEntry.getUser1().equals(otherUser) || chatEntry.getUser2().equals(otherUser)) {
                return true;
            }
        }
        return false;
    }

}

