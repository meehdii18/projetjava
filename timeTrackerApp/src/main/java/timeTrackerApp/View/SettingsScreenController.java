package timeTrackerApp.View;

import javafx.fxml.FXML;

import javafx.stage.Stage;

import javafx.scene.control.TextField;
import timeTrackerApp.Controller.SettingsController;

/**
 * This class is the controller for the settings screen of the Time Tracker application.
 * It handles the interaction between the user and the settings screen.
 * The settings screen allows the user to set the network address and port.
 */
public class SettingsScreenController {
    private Stage stage; // The stage on which the settings screen is displayed
    private SettingsController controller; // The controller for the settings

    @FXML
    private TextField networkAddress; // The text field for the network address

    @FXML
    private TextField networkPort; // The text field for the network port

    /**
     * Sets the stage for the settings screen.
     *
     * @param stage The stage to set.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Sets the controller for the settings.
     *
     * @param controller The controller to set.
     */
    public void setController(SettingsController controller) {
        this.controller = controller;
    }

    /**
     * Sets the network address in the text field.
     *
     * @param address The network address to set.
     */
    public void setNetworkAddress(String address) {
        networkAddress.setText(address);
    }

    /**
     * Sets the network port in the text field.
     *
     * @param socket The network port to set.
     */
    public void setNetworkPort(int socket) {
        networkPort.setText(String.valueOf(socket));
    }

    /**
     * Handles a click on the save button.
     * It saves the network address and port entered by the user and closes the settings screen.
     */
    @FXML
    protected void onSaveButtonClick() {
        String address = networkAddress.getText();
        int socket = Integer.parseInt(networkPort.getText());

        controller.setMainAppIP(address);
        controller.setMainAppSocket(socket);

        stage.close();
    }
}