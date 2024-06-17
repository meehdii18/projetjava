package timeTrackerApp.Controller;

import timeTrackerApp.Model.TimeTracker;
import timeTrackerApp.View.SettingsScreenController;

/**
 * The SettingsController class is responsible for managing the settings of the TimeTracker application.
 * It communicates with both the model (TimeTracker) and the view (SettingsScreenController) to update and display settings.
 *
 * This class provides methods to set the IP address and socket for the main application, and to display data from the model.
 */
public class SettingsController {

    private final TimeTracker model;
    private final SettingsScreenController settingsView;

    /**
     * Constructs a new SettingsController with the specified model and view.
     *
     * @param model The TimeTracker model to be managed.
     * @param settingsView The SettingsScreenController view to be updated.
     */
    public SettingsController(TimeTracker model, SettingsScreenController settingsView) {
        this.model = model;
        this.settingsView = settingsView;
    }

    /**
     * Sets the IP address for the main application.
     *
     * @param ip The IP address to be set.
     */
    public void setMainAppIP(String ip) {
        model.setAddress(ip);
    }

    /**
     * Sets the socket for the main application.
     *
     * @param socket The socket to be set.
     */
    public void setMainAppSocket(int socket) {
        model.setSocket(socket);
    }

    /**
     * Displays the data from the model in the view.
     */
    public void displayDataFromModel() {
        settingsView.setNetworkAddress(model.getAddress());
        settingsView.setNetworkPort(model.getSocket());
    }
}
