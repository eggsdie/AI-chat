package use_case.change_picture;

import entity.User;
import entity.UserFactory;

public class ChangePictureInteractor implements ChangePictureInputBoundary {
    private final ChangePictureUserDataAccessInterface userDataAccessObject;
    private final ChangePictureOutputBoundary userPresenter;
    private final UserFactory userFactory;

    public ChangePictureInteractor(ChangePictureUserDataAccessInterface changePictureDataAccessInterface,
                                    ChangePictureOutputBoundary changePictureOutputBoundary,
                                    UserFactory userFactory) {
        this.userDataAccessObject = changePictureDataAccessInterface;
        this.userPresenter = changePictureOutputBoundary;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(ChangePictureInputData changePictureInputData) {
        final User user = userFactory.create(changePictureInputData.getUsername(),
                userDataAccessObject.get(changePictureInputData.getUsername()).getEmail(),
                userDataAccessObject.get(changePictureInputData.getUsername()).getPassword(),
                userDataAccessObject.get(changePictureInputData.getUsername()).getPicture());

        userDataAccessObject.updateUser(user);

        final ChangePictureOutputData changePictureOutputData = new ChangePictureOutputData(user.getName(), false);
        userPresenter.prepareSuccessView(changePictureOutputData);
    }
}
