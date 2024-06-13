package mainApp.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mainApp.Controller.ManageTrackerInput;

import java.io.IOException;
import java.util.Objects;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/mainApp/View/MainApp.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 800);

        MainAppController fxmlLoaderController = fxmlLoader.getController();

        Thread input = new Thread(new ManageTrackerInput(1234, fxmlLoaderController.getController().getCompany()));

        input.start(); // TODO probablement à retoucher

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/mainApp/View/styles.css")).toExternalForm());
        stage.setTitle("Main Application");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}