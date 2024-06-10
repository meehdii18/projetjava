package timeTrackerApp.Controller;

import javafx.fxml.FXML;

import javafx.stage.Stage;

import javafx.scene.control.TextField;
import timeTrackerApp.Model.TimeTracker;

public class SettingsScreenController {
    private Stage stage;

    private TimeTracker app;

    @FXML
    private TextField networkAddress;

    @FXML
    private TextField networkPort;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setApp(TimeTracker app) {
        this.app = app;
    }

    @FXML
    protected void onSaveButtonClick() {
        String address = networkAddress.getText();
        String port = networkPort.getText();

        app.setAddress(address);
        app.setSocket(Integer.parseInt(port));

        System.out.println("IP: " + address);
        System.out.println("Socket: " + port);

        stage.close();
    }
}