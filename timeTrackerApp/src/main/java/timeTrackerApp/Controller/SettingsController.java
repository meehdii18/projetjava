package timeTrackerApp.Controller;

import timeTrackerApp.Model.TimeTracker;
import timeTrackerApp.View.SettingsScreenController;

public class SettingsController {

    private final TimeTracker model;

    private final SettingsScreenController settingsView;

    public SettingsController(TimeTracker model, SettingsScreenController settingsView) {
        this.model = model;
        this.settingsView = settingsView;
    }

    public void setMainAppIP(String ip) {
        model.setAddress(ip);
    }

    public void setMainAppSocket(int socket) {
        model.setSocket(socket);
    }

    public void displayDataFromModel() {
        settingsView.setNetworkAddress(model.getAddress());
        settingsView.setNetworkPort(model.getSocket());
    }
}
