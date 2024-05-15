package javaProject.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;

public class MainScreenController {
    @FXML
    private Label statusText;

    @FXML
    private Label dateText;

    @FXML
    private Label timeText;

    @FXML
    private TextField inputEmployeeId;

    @FXML
    private Button startButton;

    @FXML
    private ImageView startIconView;

    @FXML
    private Button stopButton;

    @FXML
    private ImageView stopIconView;

    @FXML
    private Button validateIDButton;

    @FXML
    private Button settingsButton;

    private LocalTime roundToNearestQuarterHour(LocalTime time) {
        int minute = time.getMinute();
        int second = time.getSecond();
        int totalSeconds = minute * 60 + second;
        if (totalSeconds % 900 < 450) {
            return time.minusSeconds(totalSeconds % 900);
        } else {
            return time.plusSeconds(900 - totalSeconds % 900);
        }
    }

    @FXML
    public void initialize(){
        // Le texte est mis à jour pour afficher la date actuelle de l'ordinateur
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d", Locale.ENGLISH);
        dateText.setText(LocalDate.now().format(dateFormatter));

        // Le texte est mis à jour chaque seconde pour afficher l'heure
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter approxTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0), event -> {
            LocalTime now = LocalTime.now();
            LocalTime approxTime = roundToNearestQuarterHour(now);
            timeText.setText(now.format(timeFormatter) + " (approx " + approxTime.format(approxTimeFormatter) + ")");
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Créations des icônes pour les deux bouttons
        Image startIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/javaProject/View/MainScreen/icons8-enter.png")));
        startIconView.setImage(startIcon);
        Image stopIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/javaProject/View/MainScreen/icons8-exit.png")));
        stopIconView.setImage(stopIcon);

        // Désactiver le bouton "Stop Working!" par défaut
        startButton.setDisable(true);
        stopButton.setDisable(true);

        // Placeholder de l'entrée
        inputEmployeeId.setPromptText("Employee ID...");

        // Activer le bouton "Start Working!" uniquement lorsque l'ID de l'employé est entré
        inputEmployeeId.textProperty().addListener((observable, oldValue, newValue) -> validateIDButton.setDisable(newValue.trim().isEmpty()));

    }

    @FXML
    protected void onStartButtonClick() {
        String employeeId = inputEmployeeId.getText();
        statusText.setText("Hello " + employeeId + "!");
        // Activer le bouton "Stop Working!" et désactiver le bouton "Start Working!" après avoir cliqué sur "Start Working!"
        startButton.setDisable(true);
        stopButton.setDisable(false);
    }

    @FXML
    protected void onStopButtonClick() {
        String employeeId = inputEmployeeId.getText();
        statusText.setText("Bye "+ employeeId + "!");
        // Activer le bouton "Start Working!" et désactiver le bouton "Stop Working!" après avoir cliqué sur "Stop Working!"
        startButton.setDisable(false);
        stopButton.setDisable(true);
    }

    @FXML
    protected void onValidateIdButtonClick(){
        // Activer le bouton "Start Working!" lorsqu'on a valider l'ID employé
        startButton.setDisable(false);
    }

    @FXML
    protected void onSettingsButtonClick() throws Exception {
        Stage stage = new Stage();
        SettingsScreenController controller = new SettingsScreenController();
        controller.setStage(stage);
        new javaProject.View.SettingsScreen().start(stage);
    }
}