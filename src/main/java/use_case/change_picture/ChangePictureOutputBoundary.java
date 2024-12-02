package use_case.change_picture;

import use_case.change_picture.ChangePictureOutputData;

public interface ChangePictureOutputBoundary {
    void prepareSuccessView(ChangePictureOutputData outputData);

    void prepareFailView(String errorMessage);
}
