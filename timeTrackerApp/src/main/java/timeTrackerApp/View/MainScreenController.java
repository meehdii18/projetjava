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
import java.util.Hashtable;
import java.util.Locale;
import java.util.Objects;

/**
 * Represents the time tracker screen javafx controller in a time tracker application.
 * The main screen controller is responsible for handling user interactions on the main screen.
 * Provides methods to initialize the main screen, handle button clicks, and update the display.
 */
public class MainScreenController {
    /**
     * The base number of characters from the employee UUID to display in the comboBox
     */
    public static final int BASIC_EMPLOYEE_ID_SIZE = 4;

    /**
     * The status text label on the main screen.
     */
    @FXML
    private Label statusText;

    /**
     * The date text label on the main screen.
     */
    @FXML
    private Label dateText;

    /**
     * The time text label on the main screen.
     */
    @FXML
    private Label timeText;

    /**
     * The employee combo box on the main screen.
     */
    @FXML
    private ComboBox<String> employeeComboBox;

    /**
     * The clock button on the main screen.
     */
    @FXML
    private Button clockButton;

    /**
     * The clock icon view on the main screen.
     */
    @FXML
    private ImageView clockIconView;

    /**
     * The settings button on the main screen.
     */
    @FXML
    private Button settingsButton;

    /**
     * The update button on the main screen.
     */
    @FXML
    private Button updateButton;

    // Other class fields

    /**
     * The table mapping custom employee IDs to actual employee IDs.
     */
    private Hashtable<String, String> comboBoxIdTable;

    /**
     * The approximate time, rounded to the nearest quarter-hour.
     */
    private LocalTime approxTime;

    /**
     * The controller for the time tracker.
     */
    private TimeTrackerController controller;

    /**
     * Rounds a time to the nearest quarter-hour.
     *
     * @param time The time to round.
     * @return The time rounded to the nearest quarter-hour.
     */
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

    /**
     * Initializes the main screen.
     * This method is called after all @FXML annotated members have been injected.
     */
    @FXML
    public void initialize(){

        comboBoxIdTable = new Hashtable<>();

        // Text is updated to display the actual date of the computer
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d", Locale.ENGLISH);
        dateText.setText(LocalDate.now().format(dateFormatter));

        // The text is updated each second to keep up with the hour
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

        // initialize the MVC controller
        TimeTrackerController controller = new TimeTrackerController(TimeTracker.deserializeLocalData("timeTrackerApp/src/main/resources/data/timeTracker/localData.ser"), this);
        setController(controller);

        // add employees in the comboBox selection
        for (String employeeId : controller.getEmployees().keySet()) {
            addEmployeeToComboBox(employeeId);
        }

        // creation of icons for the buttons
        Image startIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/timeTrackerApp/View/MainScreen/clock.png")));
        clockIconView.setImage(startIcon);

        // disable the clock In/Out button by default
        clockButton.setDisable(true);

        // set the event for the updateButton click
        updateButton.setOnAction(event -> onUpdateEmployeesButtonClick());

        // set the event for the comboBox selection
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

    /**
     * Adds an employee to the combo box.
     *
     * @param employeeId The ID of the employee to add.
     */
    private void addEmployeeToComboBox(String employeeId) {
            String customEmp = generateCustomEmployeeId(employeeId, BASIC_EMPLOYEE_ID_SIZE); // TODO : change by constant

            if (comboBoxIdTable.get(customEmp) == null) {
                comboBoxIdTable.put(customEmp, employeeId);
            } else {
                String conflictEmployeeId = comboBoxIdTable.get(customEmp);

                String customEmpId1 = new String(customEmp);
                String customEmpId2 = new String(customEmp);

                int newIdSize = BASIC_EMPLOYEE_ID_SIZE + 1;

                while (customEmpId1.equals(customEmpId2)) {
                    customEmpId1 = generateCustomEmployeeId(conflictEmployeeId,newIdSize);
                    customEmpId2 = generateCustomEmployeeId(employeeId, newIdSize);
                    newIdSize += 1;
                }
                comboBoxIdTable.put(customEmpId1, conflictEmployeeId);
                comboBoxIdTable.put(customEmpId2, employeeId);
            }

            employeeComboBox.getItems().add(customEmp);
    }

    /**
     * Generates a custom employee ID.
     *
     * @param employeeId The actual employee ID.
     * @param i The number of characters to include from the actual employee ID.
     * @return The custom employee ID.
     */
    private String generateCustomEmployeeId(String employeeId, int i) {
        return controller.getEmployees().get(employeeId) + " (" + employeeId.substring(0,i) + "...)";
    }

    /**
     * Displays a message on the status text label.
     *
     * @param text The message to display.
     */
    public void display(String text) {

        statusText.setText(text);
    }

    /**
     * Handles the selection of an employee in the combo box.
     */
    private void employeeSelected() {
        if (employeeComboBox.getValue() != null) {
            clockButton.setDisable(false);

        String employeeDetails = controller.getEmployeeDetails(comboBoxIdTable.get(employeeComboBox.getValue()));

        statusText.setText("Bonjour, " + employeeDetails + " !");
        }
    }

    /**
     * Handles a click on the clock button.
     */
    @FXML
    protected void onClockButtonClick() {
        String employeeId = employeeComboBox.getValue();

        employeeComboBoxReset();

        controller.newClocking(comboBoxIdTable.get(employeeId), LocalDate.now(), approxTime);
    }

    /**
     * Resets the employee combo box.
     */
    private void employeeComboBoxReset() {

        employeeComboBox.getSelectionModel().clearSelection();

        statusText.setText("");

        clockButton.setDisable(true);

    }

    /**
     * Handles a click on the update employees button.
     */
    @FXML
    protected void onUpdateEmployeesButtonClick() {

        controller.fetchDistantEmployees();

        employeeComboBoxReset();

        comboBoxIdTable.clear();

        employeeComboBox.getItems().clear();

        for (String employeeId : controller.getEmployees().keySet()) {

            addEmployeeToComboBox(employeeId);
        }
    }

    /**
     * Handles a click on the settings button.
     *
     * @throws Exception If an error occurs while loading the settings screen.
     */
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

    /**
     * Sets the controller for the time tracker.
     *
     * @param controller The controller for the time tracker.
     */
    public void setController(TimeTrackerController controller) {
        this.controller = controller;
    }


}