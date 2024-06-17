package mainApp.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Represents the main application view in a time tracker application.
 * The main application view is a JavaFX application that loads an FXML file to create the user interface.
 * Provides methods to start the application, handle the closing of the application window, and launch the application.
 */
public class MainApp extends Application {

    /**
     * Represents the main application view in a time tracker application.
     * The main application view is a JavaFX application that loads an FXML file to create the user interface.
     * Provides methods to start the application, handle the closing of the application window, and launch the application.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/mainApp/View/MainApp.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 800);

        MainAppController fxmlLoaderController = fxmlLoader.getController();

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/mainApp/View/styles.css")).toExternalForm());
        stage.setTitle("Main Application");
        stage.setScene(scene);
        stage.setOnCloseRequest(windowEvent -> {
            fxmlLoaderController.getController().stopServerThread();
        });
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