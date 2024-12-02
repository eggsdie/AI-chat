package interface_adapter.change_picture;

import use_case.change_picture.ChangePictureInputBoundary;
import use_case.change_picture.ChangePictureInputData;

public class ChangePictureController {
    private final ChangePictureInputBoundary userChangePictureUseCaseInteractor;

    public ChangePictureController(ChangePictureInputBoundary userChangePictureUseCaseInteractor) {
        this.userChangePictureUseCaseInteractor = userChangePictureUseCaseInteractor;
    }

    public void execute(String profilePicture, String username) {
        final ChangePictureInputData changePictureInputData = new ChangePictureInputData(profilePicture, username);

        userChangePictureUseCaseInteractor.execute(changePictureInputData);
    }
}
