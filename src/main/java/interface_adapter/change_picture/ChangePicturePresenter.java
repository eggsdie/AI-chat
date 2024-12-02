package interface_adapter.change_picture;

import interface_adapter.settings.SettingsViewModel;
import use_case.change_picture.ChangePictureOutputBoundary;
import use_case.change_picture.ChangePictureOutputData;

public class ChangePicturePresenter implements ChangePictureOutputBoundary {
    private final SettingsViewModel settingsViewModel;

    public ChangePicturePresenter(SettingsViewModel settingsViewModel) {
        this.settingsViewModel = settingsViewModel;
    }

    @Override
    public void prepareSuccessView(ChangePictureOutputData outputData) {
        // currently there isn't anything to change based on the output data,
        // since the output data only contains the username, which remains the same.
        // We still fire the property changed event, but just to let the view know that
        // it can alert the user that their picture was changed successfully..
        settingsViewModel.firePropertyChanged("picture");

    }

    @Override
    public void prepareFailView(String error) {
        // note: this use case currently can't fail
    }
}
