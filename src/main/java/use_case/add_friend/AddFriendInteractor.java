//package use_case.add_friend;
//
//import entity.Chat;
//import entity.Friend;
//
//public class AddFriendInteractor implements AddFriendInputBoundary {
//
//    private final AddFriendUserDataAccessInterface addFriendUserDataAccessInterface;
//    private final AddFriendOutputBoundary userPresenter;
//
//    public AddFriendInteractor(AddFriendUserDataAccessInterface addFriendUserDataAccessInterface,
//                               AddFriendOutputBoundary addFriendOutputBoundary) {
//        this.addFriendUserDataAccessInterface = addFriendUserDataAccessInterface;
//        this.userPresenter = addFriendOutputBoundary;
//    }
//
//    public void execute(AddFriendInputData addFriendInputData) {
//        if (!addFriendUserDataAccessInterface.userExists(addFriendInputData.getUser())) {
//            userPresenter.prepareFailView("User does not exist.");
//        }
//        else if (addFriendUserDataAccessInterface.friendExists(addFriendInputData.getUser())) {
//            userPresenter.prepareFailView("Friend already added.");
//        }
//        else if (addFriendUserDataAccessInterface.chatWithYourself(addFriendUserDataAccessInterface.getActiveUser(),
//                addFriendInputData.getUser())) {
//            userPresenter.prepareFailView("Can't make a chat with yourself!");
//        }
//        else {
//            final Friend friend = new Friend(addFriendInputData.getUser());
//            final Chat chat = new Chat(friend);
//            addFriendUserDataAccessInterface.saveFriend(friend, chat);
//
//            final AddFriendOutputData addChatOutputData = new AddFriendOutputData(false);
//            userPresenter.prepareSuccessView(addChatOutputData);
//        }
//    }
//
//    public void switchToLoggedInView() {
//        userPresenter.switchToLoggedInView();
//    }
//}
