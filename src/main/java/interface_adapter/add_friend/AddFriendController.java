package interface_adapter.add_friend;

import use_case.add_friend.AddFriendInputBoundary;
import use_case.add_friend.AddFriendInputData;

public class AddFriendController {

    private final AddFriendInputBoundary addFriendUseCaseInteractor;

    public AddFriendController(AddFriendInputBoundary addFriendUseCaseInteractor) {
        this.addFriendUseCaseInteractor = addFriendUseCaseInteractor;
    }

    public void execute(String username) {
        final AddFriendInputData addFriendInputData = new AddFriendInputData(username);

        addFriendUseCaseInteractor.execute(addFriendInputData);
    }

    /**
     * Executes the "switch to LoginView" Use Case.
     */
    public void switchToLoggedInView() {
        addFriendUseCaseInteractor.switchToLoggedInView();
    }

}
