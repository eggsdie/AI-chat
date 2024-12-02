package use_case.settings;

public class SettingsInteractor implements SettingsInputBoundary {
    private final SettingsOutputBoundary settingsPresenter;

    public SettingsInteractor(SettingsOutputBoundary settingsOutputBoundary) {
        this.settingsPresenter = settingsOutputBoundary;
    }

    @Override
    public void execute(SettingsInputData settingsInputData) {
        final SettingsOutputData settingsOutputData = new SettingsOutputData(settingsInputData.getUsername(),
                settingsInputData.getEmail(), settingsInputData.getPassword(), settingsInputData.getPicture(), false);
        settingsPresenter.prepareSuccessView(settingsOutputData);
    }

    @Override
    public void switchToChatListView() {
        settingsPresenter.switchToChatListView();
    }
}
