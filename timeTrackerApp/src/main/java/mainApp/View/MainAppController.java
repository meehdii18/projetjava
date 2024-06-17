package mainApp.View;

import common.Model.DisplayClocking;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalTimeStringConverter;
import mainApp.Controller.ManageCompany;
import mainApp.Controller.ManageTrackerInput;
import mainApp.Model.*;
import javafx.collections.ObservableList;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Hashtable;

/**
 * Represents the main app javafx controller in a time tracker application.
 * The main app controller is responsible for handling user interactions on the main app.
 * Provides methods to initialize the main screen, handle button clicks, and update the display.
 */
public class MainAppController {
    /**
     * The panel that contains all the tabs
     */
    @FXML
    private TabPane tabPane;

    /**
     * Label for the IP
     */
    @FXML
    private Label ipLabel;

    /**
     * Label for the socket
     */
    @FXML
    private Label socketLabel;

    /**
     * Search field to search a specific employee via name
     */
    @FXML
    private TextField searchEmployeeField;

    /**
     * Button to confirm the addition of an employee
     */
    @FXML
    private Button addEmployeeButton;

    /**
     * Button to remove an amployéee
     */
    @FXML
    private Button removeEmployeeButton;

    // Employees tab

    /**
     * Table with all the employees
     */
    @FXML
    private TableView<Employee> employeesTable;

    /**
     * Column for the first name
     */
    @FXML
    private TableColumn<Employee, String> firstNameColumnEmployees;

    /**
     * Column for the last name
     */
    @FXML
    private TableColumn<Employee, String> lastNameColumnEmployees;

    /**
     * Column for the department name
     */
    @FXML
    private TableColumn<Employee, String> departmentEmployees;

    /**
     * Column for the time in
     */
    @FXML
    private TableColumn<Employee, LocalTime> inTimeEmployees;

    /**
     * Column for the time out
     */
    @FXML
    private TableColumn<Employee, LocalTime> outTimeEmployees;



    // Time tracking tab

    /**
     * Table for the time tracking of all employees
     */
    @FXML
    private TableView<DisplayClocking> timeTrackingTable;

    /**
     * Tab of the app
     */
    @FXML
    private Tab timeTrackingTab;

    /**
     * Column for the id
     */
    @FXML
    private TableColumn<DisplayClocking, String> idColumnTT;

    /**
     * Column for the last name
     */
    @FXML
    private TableColumn<DisplayClocking, String> lastNameColumnTT;

    /**
     * Column for the first name
     */
    @FXML
    private TableColumn<DisplayClocking, String> firstNameColumnTT;

    /**
     * Column for the date
     */
    @FXML
    private TableColumn<DisplayClocking, String> dateColumnTT;

    /**
     * Column for the hour
     */
    @FXML
    private TableColumn<DisplayClocking,String> hourColumnTT;

    /**
     * Column for the type (in/out)
     */
    @FXML
    private TableColumn<DisplayClocking,String> typeColumnTT;

    @FXML
    private Button todayTrackingButton;

    @FXML
    private Button allTimeTrackingButton;



    // Input pour l'ajout des employés

    /**
     * Input for adding and employee : first name
     */
    @FXML
    private TextField inputFirstName;

    /**
     * Input for adding and employee : last name
     */
    @FXML
    private TextField inputLastName;

    /**
     * Input for adding and employee : department name
     */
    @FXML
    private ComboBox<String> inputDepartment;

    /**
     * Input for adding and employee : time in
     */
    @FXML
    private TextField inputIn;

    /**
     * Input for adding and employee : time out
     */
    @FXML
    private TextField inputOut;

    /**
     * Input for adding and employee : salary
     */
    @FXML
    private TextField inputSalary;

    // Onglet infos détaillées

    /**
     * Table for the detailled view of each employees
     */
    @FXML
    private TableView<Employee> employeeDetailsTable;

    /**
     * Tab for the detailled view of each employees
     */
    @FXML
    private Tab employeeDetailsTab;

    /**
     * Column for the detailled view: id
     */
    @FXML
    private TableColumn<Employee, String> idColumn;

    /**
     * Column for the detailled view: first name
     */
    @FXML
    private TableColumn<Employee, String> firstNameColumn;

    /**
     * Column for the detailled view: last name
     */
    @FXML
    private TableColumn<Employee, String> lastNameColumn;

    /**
     * Column for the detailled view: department name
     */
    @FXML
    private TableColumn<Employee, String> departmentColumn;

    /**
     * Column for the detailled view: salary
     */
    @FXML
    private TableColumn<Employee, Float> salaryColumn;

    /**
     * Column for the detailled view: time in
     */
    @FXML
    private TableColumn<Employee, LocalTime> startHourColumn;

    /**
     * Column for the detailled view: time out
     */
    @FXML
    private TableColumn<Employee, LocalTime> endHourColumn;

    /**
     * Column for the detailled view: extra hour
     */
    @FXML
    private TableColumn<Employee, LocalTime> extraHoursColumn;

    // Pointages de la vue détaillée

    /**
     * Table for the time tracking of a detailled employee
     */
    @FXML
    private TableView<DisplayClocking> timeTrackingDetailsTable;

    /**
     * Column for the type of the tracking (in/out)
     */
    @FXML
    private TableColumn<DisplayClocking, String> complexViewType;

    /**
     * Column for the date of the tracking
     */
    @FXML
    private TableColumn<DisplayClocking, String> complexViewDate;

    /**
     * Column for the hour of the tracking
     */
    @FXML
    private TableColumn<DisplayClocking, String> complexViewHour;

    /**
     * Column for the save button in the editing tab
     */
    @FXML
    private Button saveChangesButton;

    /**
     * Current selected employee by the user
     */
    private Employee selectedEmployee;

    /**
     * Controller for JavaFx
     */
    private ManageCompany controller;

    /**
     * Update the controller
     */
    public void setController(ManageCompany controller) {
        this.controller = controller;
    }

    /**
     * Get the controller
     */
    public ManageCompany getController() {
        return controller;
    }


    /**
     * Initialize all the component for each tab
     */
    @FXML
    public void initialize() {
        firstNameColumnEmployees.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumnEmployees.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        departmentEmployees.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        inTimeEmployees.setCellValueFactory(new PropertyValueFactory<>("startHour"));
        outTimeEmployees.setCellValueFactory(new PropertyValueFactory<>("endHour"));

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        startHourColumn.setCellValueFactory(new PropertyValueFactory<>("startHour"));
        endHourColumn.setCellValueFactory(new PropertyValueFactory<>("endHour"));
        extraHoursColumn.setCellValueFactory(new PropertyValueFactory<>("extraHour"));

        idColumnTT.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumnTT.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumnTT.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        dateColumnTT.setCellValueFactory(new PropertyValueFactory<>("date"));
        hourColumnTT.setCellValueFactory(new PropertyValueFactory<>("time"));
        typeColumnTT.setCellValueFactory(new PropertyValueFactory<>("type"));

        complexViewType.setCellValueFactory(new PropertyValueFactory<>("type"));
        complexViewDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        complexViewHour.setCellValueFactory(new PropertyValueFactory<>("time"));


        ManageCompany controller = new ManageCompany(Company.deserializeCompany("timeTrackerApp/src/main/resources/data/company/Polytech.ser"),this);
        setController(controller);

        Thread input = new Thread(new ManageTrackerInput(getController().getCompany()));

        input.start();

        try {
            ipLabel.setText(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        socketLabel.setText(String.valueOf(controller.getSocket()));

        for (String departmentName : controller.getDepartments().keySet()) {
            inputDepartment.getItems().add(departmentName);
        }

        // DEBUG
        System.out.println(controller.getCompanyName()+" company loading...");
        System.out.println("Company object deserialized");
        Hashtable<String,Department> departmentsList =  controller.getDepartments();

        // Ajout de tous les employés dans la table
        for (Department department : controller.getDepartments().values()) {
            System.out.println("Département : " + department.getDepartmentName());
            for (Employee employee : department.getEmployeesList().values()) {
                employeesTable.getItems().add(employee);
                System.out.println("Ajout de " + employee.getFirstName() + " " + employee.getLastName());
            }
        }


        employeesTable.setRowFactory(tv -> {
            TableRow<Employee> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Employee rowData = row.getItem();
                    showEmployeeDetails(rowData);
                }
            });
            return row;
        });


        inputIn.textProperty().addListener((obs, oldText, newText) -> {
            // Si le nouveau texte ne correspond pas au format "HH : MM", désactivez le bouton addEmployeeButton
            // Si le nouveau texte correspond au format "HH : MM", activez le bouton addEmployeeButton
            addEmployeeButton.setDisable(!newText.matches("\\d{2}:\\d{2}"));
        });

        inputOut.textProperty().addListener((obs, oldText, newText) -> {
            // Si le nouveau texte ne correspond pas au format "HH : MM", désactivez le bouton addEmployeeButton
            // Si le nouveau texte correspond au format "HH : MM", activez le bouton addEmployeeButton
            addEmployeeButton.setDisable(!newText.matches("\\d{2}:\\d{2}"));
        });

        inputSalary.textProperty().addListener((obs, oldText, newText) -> {
            // Si le nouveau texte n'est pas un nombre, désactivez le bouton addEmployeeButton
            // Si le nouveau texte est un nombre, activez le bouton addEmployeeButton
            addEmployeeButton.setDisable(!newText.matches("\\d+"));
        });

        allTimeTrackingButton.setDisable(true);

        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab == employeeDetailsTab && selectedEmployee == null) {
                // Si l'utilisateur tente de sélectionner l'onglet des détails de l'employé sans avoir sélectionné un employé,
                // annulez le changement de sélection
                tabPane.getSelectionModel().select(oldTab);
            }
        });

        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab == timeTrackingTab) {
                updateTimeTrackingTable();
            }
        });

        // Rendre le tableau modifiable
        employeeDetailsTable.setEditable(true);

// Rendre les colonnes modifiables
        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Employee, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setFirstName(t.getNewValue())
        );

        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Employee, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setLastName(t.getNewValue())
        );

        salaryColumn.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        salaryColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Employee, Float> t) -> {
                    Employee employee = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    employee.setSalary(t.getNewValue());
                }
        );

        startHourColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalTimeStringConverter()));
        startHourColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Employee, LocalTime> t) -> {
                    Employee employee = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    employee.setStartHour(t.getNewValue());
                }
        );

        endHourColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalTimeStringConverter()));
        endHourColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Employee, LocalTime> t) -> {
                    Employee employee = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    employee.setEndHour(t.getNewValue());
                }
        );


        ObservableList<String> departmentNames = FXCollections.observableArrayList(controller.getDepartments().keySet());

        // Configurer la colonne du département pour utiliser une ComboBox
        departmentColumn.setCellFactory(ComboBoxTableCell.forTableColumn(departmentNames));
        departmentColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Employee, String> t) -> {
                    Employee employee = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    employee.setDepartmentName(t.getNewValue());
                }
        );

        // Recherche d'employé
        ObservableList<Employee> allEmployees = FXCollections.observableArrayList();
        for (Department department : controller.getDepartments().values()) {
            allEmployees.addAll(department.getEmployeesList().values());
        }

        // Ajoutez un ChangeListener au champ searchEmployeeField
        searchEmployeeField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                // Si le champ de recherche est vide, réinitialisez la table avec tous les employés
                employeesTable.setItems(allEmployees);
            } else {
                // Sinon, filtrez la liste des employés en fonction du texte entré
                ObservableList<Employee> filteredEmployees = FXCollections.observableArrayList();
                for (Employee employee : allEmployees) {
                    if (employee.getFirstName().toLowerCase().contains(newValue.toLowerCase())
                            || employee.getLastName().toLowerCase().contains(newValue.toLowerCase())) {
                        filteredEmployees.add(employee);
                    }
                }
                employeesTable.setItems(filteredEmployees);
            }
        });

        tabPane.getTabs().remove(employeeDetailsTab);

        tabPane.getSelectionModel().select(0); // Select the first tab
        addEmployeeButton.setOnAction(event -> addEmployee());
        removeEmployeeButton.setOnAction(event -> deleteEmployee());
        saveChangesButton.setOnAction(event -> saveChanges());
        todayTrackingButton.setOnAction(event -> todayTracking());
        allTimeTrackingButton.setOnAction(event -> allTimeTracking());

    }

    /**
     * Add an employee to the company and update the table.
     */
    @FXML
    protected void addEmployee() {
        String firstName = inputFirstName.getText();
        String lastName = inputLastName.getText();
        String departmentName = inputDepartment.getValue();
        LocalTime start_hour = LocalTime.parse(inputIn.getText());
        LocalTime end_hour = LocalTime.parse(inputOut.getText());
        String salaryString = inputSalary.getText();
        int salary = Integer.parseInt(salaryString);

        // Ajoutez l'employé à l'objet Company
        String employeeId = controller.addEmployee(firstName, lastName, departmentName, salary, start_hour, end_hour);

        // Ajoutez l'employé à la liste des items de la table
        employeesTable.getItems().add(controller.getEmployee(employeeId));
        updateTimeTrackingTable();
    }

    /**
     * Delete the selected employee from the company and update the table.
     */
    @FXML
    protected void deleteEmployee() {
        // Obtenir l'employé sélectionné
        Employee selectedEmployee = employeesTable.getSelectionModel().getSelectedItem();

        if (selectedEmployee != null) {
            // Supprimez l'employé de l'objet Company
            controller.deleteEmployee(selectedEmployee.getId());

            // Supprimez l'employé de la table
            employeesTable.getItems().remove(selectedEmployee);
        } else {
            // Aucun employé n'est sélectionné.
            System.out.println("Aucun employé sélectionné.");
        }
        updateTimeTrackingTable();
    }

    /**
     * Show the details of the selected employee.
     * @param employee The selected employee.
     */
    private void showEmployeeDetails(Employee employee) {
        // Mettez à jour l'employé sélectionné
        this.selectedEmployee = employee;

        ObservableList<Employee> employeeDetails = FXCollections.observableArrayList();
        employeeDetails.add(employee);
        employeeDetailsTable.setItems(employeeDetails);

        ObservableList<DisplayClocking> clockingDetails = FXCollections.observableArrayList();
        clockingDetails.addAll(controller.getClocking(employee.getId()));
        timeTrackingDetailsTable.setItems(clockingDetails);

        // Ajoutez l'onglet au TabPane
        tabPane.getTabs().add(employeeDetailsTab);

        // Sélectionnez l'onglet
        tabPane.getSelectionModel().select(employeeDetailsTab);

    }

    /**
     * Save the changes made to the employee details.
     */
    private void saveChanges() {

        controller.updateData(selectedEmployee);
        employeesTable.refresh();
        employeeDetailsTable.refresh();
        timeTrackingDetailsTable.refresh();
    }

    /**
     * Refresh the time tracking table.
     */
    public void updateTimeTrackingTable() {

        ObservableList<DisplayClocking> clockingDetails = FXCollections.observableArrayList();

        clockingDetails.addAll(controller.getClocking());
        timeTrackingTable.setItems(clockingDetails);

        timeTrackingTable.refresh();
    }

    /**
     * Filter the time tracking table to show only today's entries.
     */
    public void todayTracking() {
        LocalDate today = LocalDate.now();

        ObservableList<DisplayClocking> filtered = timeTrackingTable.getItems().filtered(clocking -> clocking.getDate().equals(today));

        timeTrackingTable.setItems(filtered);

        timeTrackingTable.refresh();

        allTimeTrackingButton.setDisable(false);
        todayTrackingButton.setDisable(true);
    }

    /**
     * Show all entries in the time tracking table.
     */
    public void allTimeTracking() {

        updateTimeTrackingTable();

        todayTrackingButton.setDisable(false);
        allTimeTrackingButton.setDisable(true);
    }
}
