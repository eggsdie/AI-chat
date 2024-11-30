package use_case.add_friend;

public interface AddFriendInputBoundary {
    void execute(AddFriendInputData addFriendInputData, String messagePreview);
    // boolean removeChat(String name);

}

