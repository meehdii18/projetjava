package timeTrackerApp.View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import timeTrackerApp.Controller.SettingsController;
import timeTrackerApp.Controller.TimeTrackerController;
import timeTrackerApp.Model.TimeTracker;

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
    private ComboBox<String> employeeComboBox;

    @FXML
    private Button clockButton;

    @FXML
    private ImageView clockIconView;

    @FXML
    private Button settingsButton;

    @FXML
    private Button updateButton;

    private LocalTime approxTime;

    private TimeTrackerController controller;

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
            LocalTime now = LocalTime.now().withNano(0);
            if (roundToNearestQuarterHour(now) != now) {
                approxTime = roundToNearestQuarterHour(now);
            }
            timeText.setText(now.format(timeFormatter) + " (approx " + approxTime.format(approxTimeFormatter) + ")");
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        TimeTrackerController controller = new TimeTrackerController(TimeTracker.deserializeLocalData("timeTrackerApp/src/main/resources/data/timeTracker/localData.ser"), this);
        setController(controller);

        for (String employeeId : controller.getEmployees().keySet()) {
            employeeComboBox.getItems().add(employeeId);
        }

        // Créations des icônes pour les deux boutons
        Image startIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/timeTrackerApp/View/MainScreen/clock.png")));
        clockIconView.setImage(startIcon);

        // Désactiver le bouton "Clock In/Out" par défaut
        clockButton.setDisable(true);



        employeeComboBox.setOnAction(event -> employeeSelected());
        employeeComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("Select your ID");
                } else {
                    setText(item);
                }
            }
        });

    }

    private void employeeSelected() {
        if (employeeComboBox.getValue() != null) {
            clockButton.setDisable(false);

        String employeeDetails = controller.getEmployeeDetails(employeeComboBox.getValue());

        statusText.setText("Bonjour, " + employeeDetails + " !");
        }
    }

    @FXML
    protected void onClockButtonClick() {
        String employeeId = employeeComboBox.getValue();

        employeeComboBoxReset();

        controller.newClocking(employeeId, LocalDate.now(), approxTime);

        onUpdateEmployeesButtonClick(); // TODO : remove when button added
    }

    private void employeeComboBoxReset() {

        employeeComboBox.getSelectionModel().clearSelection();

        statusText.setText("");

        clockButton.setDisable(true);

    }

    @FXML
    protected void onUpdateEmployeesButtonClick() {

        controller.fetchDistantEmployees();

        employeeComboBoxReset();

        employeeComboBox.getItems().clear();

        for (String employeeId : controller.getEmployees().keySet()) {
            employeeComboBox.getItems().add(employeeId);
        }
    }

    @FXML
    protected void onSettingsButtonClick() throws Exception {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/timeTrackerApp/View/SettingsScreen/SettingsScreen.fxml"));
        Parent root = fxmlLoader.load();

        SettingsScreenController fxmlLoaderController = fxmlLoader.getController();

        SettingsController controller = new SettingsController(this.controller.getModel(), fxmlLoaderController);
        fxmlLoaderController.setController(controller);

        controller.displayDataFromModel();

        fxmlLoaderController.setStage(stage);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void setController(TimeTrackerController controller) {
        this.controller = controller;
    }


}