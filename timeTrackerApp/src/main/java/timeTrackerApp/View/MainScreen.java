package timeTrackerApp.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainScreen extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainScreen.class.getResource("/timeTrackerApp/View/MainScreen/MainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 480);

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/timeTrackerApp/View/MainScreen/styles.css")).toExternalForm());
        stage.setTitle("Time Tracker App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}