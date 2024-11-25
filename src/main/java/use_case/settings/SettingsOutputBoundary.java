package use_case.settings;

public interface SettingsOutputBoundary {
    void prepareSuccessView(SettingsOutputData outputData);

    void prepareFailView(String errorMessage);

    void switchToChatListView();
}
