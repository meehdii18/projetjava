package timeTrackerApp.View;

import timeTrackerApp.Controller.SettingsScreenController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SettingsScreen extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/timeTrackerApp/View/SettingsScreen/SettingsScreen.fxml"));
        Parent root = loader.load();

        SettingsScreenController controller = loader.getController();
        controller.setStage(stage);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/timeTrackerApp/View/SettingsScreen/styles.css")).toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Network Settings");

        stage.show();
    }
}