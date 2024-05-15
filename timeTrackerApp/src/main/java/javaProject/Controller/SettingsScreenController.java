package javaProject.Controller;

import javafx.fxml.FXML;

import javafx.stage.Stage;

import javafx.scene.control.TextField;

public class SettingsScreenController {
    private Stage stage;

    @FXML
    private TextField networkAddress;

    @FXML
    private TextField networkPort;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    protected void onSaveButtonClick() {
        String address = networkAddress.getText();
        String port = networkPort.getText();

        System.out.println("IP: " + address);
        System.out.println("Socket: " + port);

        stage.close();
    }
}