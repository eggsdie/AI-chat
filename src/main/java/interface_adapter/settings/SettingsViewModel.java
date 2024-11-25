package interface_adapter.settings;

import interface_adapter.ViewModel;

public class SettingsViewModel extends ViewModel<SettingsState> {
    public SettingsViewModel() {
        super("settings");
        setState(new SettingsState());
    }
}
