package timeTrackerApp.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Represents the main screen view in a time tracker application.
 * The main screen view is a JavaFX application that loads an FXML file to create the user interface.
 * Provides methods to start the application and launch the application.
 */
public class MainScreen extends Application {

    /**
     * Starts the JavaFX application.
     * Loads the FXML file for the main screen view, creates a new scene with the loaded FXML, and displays the scene in a new stage.
     *
     * @param stage The primary stage for this application, onto which the application scene can be set.
     * @throws IOException If an I/O error occurs while loading the FXML file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainScreen.class.getResource("/timeTrackerApp/View/MainScreen/MainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 480);

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/timeTrackerApp/View/MainScreen/styles.css")).toExternalForm());
        stage.setTitle("Time Tracker App");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Launches the JavaFX application.
     * This method is typically called from the main method().
     * It must not be called more than once or an exception will be thrown.
     *
     * @param args The command line arguments passed to the application.
     *             An application may get these parameters using the Application.getParameters() method.
     */
    public static void main(String[] args) {
        launch();
    }
}