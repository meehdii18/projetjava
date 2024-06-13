package timeTrackerApp.View;

import javafx.fxml.FXML;

import javafx.stage.Stage;

import javafx.scene.control.TextField;
import timeTrackerApp.Controller.SettingsController;

public class SettingsScreenController {
    private Stage stage;

    private SettingsController controller;

    @FXML
    private TextField networkAddress;

    @FXML
    private TextField networkPort;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setController(SettingsController controller) {
        this.controller = controller;
    }

    public void setNetworkAddress(String address) {
        networkAddress.setText(address);
    }

    public void setNetworkPort(int socket) {
        networkPort.setText(String.valueOf(socket));
    }

    @FXML
    protected void onSaveButtonClick() {
        String address = networkAddress.getText();
        int socket = Integer.parseInt(networkPort.getText());

        controller.setMainAppIP(address);
        controller.setMainAppSocket(socket);

        System.out.println("IP: " + address);
        System.out.println("Socket: " + socket);

        stage.close();
    }
}