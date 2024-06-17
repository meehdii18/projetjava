package timeTrackerApp.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Represents the settings screen view in a time tracker application.
 * The settings screen view is a JavaFX application that loads an FXML file to create the user interface.
 * Provides methods to start the application.
 */
public class SettingsScreen extends Application {

    /**
     * Starts the JavaFX application.
     * Loads the FXML file for the settings screen view, creates a new scene with the loaded FXML, and displays the scene in a new stage.
     *
     * @param stage The primary stage for this application, onto which the application scene can be set.
     * @throws IOException If an I/O error occurs while loading the FXML file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/timeTrackerApp/View/SettingsScreen/SettingsScreen.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/timeTrackerApp/View/SettingsScreen/styles.css")).toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Network Settings");

        stage.show();
    }
}