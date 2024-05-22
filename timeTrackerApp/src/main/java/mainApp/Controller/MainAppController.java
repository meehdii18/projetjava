package mainApp.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

public class MainAppController {
    @FXML
    private TabPane tabPane;

    @FXML
    public void initialize() {
        tabPane.getSelectionModel().select(1); // Sélectionne le premier onglet
    }
}