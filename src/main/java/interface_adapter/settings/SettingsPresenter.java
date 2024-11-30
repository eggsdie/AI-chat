package interface_adapter.settings;

import interface_adapter.ViewManagerModel;
import interface_adapter.add_friend.ChatListViewModel;
import use_case.settings.SettingsOutputData;
import use_case.settings.SettingsOutputBoundary;

public class SettingsPresenter implements SettingsOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final SettingsViewModel settingsViewModel;
    private final ChatListViewModel chatListViewModel;

    public SettingsPresenter(ViewManagerModel viewManagerModel,
                             SettingsViewModel settingsViewModel, ChatListViewModel chatListViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.settingsViewModel = settingsViewModel;
        this.chatListViewModel = chatListViewModel;
    }

    @Override
    public void prepareSuccessView(SettingsOutputData outputData) {
        final SettingsState settingsState = settingsViewModel.getState();
        settingsState.setUsername(outputData.getUsername());
        settingsState.setEmail(outputData.getEmail());
        this.settingsViewModel.setState(settingsState);
        this.settingsViewModel.firePropertyChanged();

        this.viewManagerModel.setState(settingsViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {

    }

    public void switchToChatListView() {
        viewManagerModel.setState(chatListViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

}
