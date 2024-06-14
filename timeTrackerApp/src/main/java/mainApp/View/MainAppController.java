// TODO : standardiser et déplacer dans view, remplacer par un controller standard

package mainApp.View;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import mainApp.Controller.ManageCompany;
import mainApp.Model.*;
import javafx.collections.ObservableList;
import java.time.LocalTime;
import java.util.Hashtable;
import java.util.UUID;


public class MainAppController {
    @FXML
    private TabPane tabPane;

    @FXML
    private Label ipLabel;

    @FXML
    private Label socketLabel;

    // TODO : Ajouter un TextField pour changer le socketLabel

    @FXML
    private TextField searchEmployeeField;

    @FXML
    private Button addEmployeeButton;

    @FXML
    private Button removeEmployeeButton;

    // Employees tab
    @FXML
    private TableView<Employee> employeesTable;

    @FXML
    private TableColumn<Employee, String> firstNameColumnEmployees;

    @FXML
    private TableColumn<Employee, String> lastNameColumnEmployees;

    @FXML
    private TableColumn<Employee, String> departementEmployees;

    @FXML
    private TableColumn<Employee, LocalTime> inTimeEmployees;

    @FXML
    private TableColumn<Employee, LocalTime> outTimeEmployees;



    // Time tracking tab
    // TODO : changer tout les '?' avec le bon type de données
    @FXML
    private TableView<?> timeTrakingTable;

    @FXML
    private TableColumn<?, ?> lastNameColumnTT;

    @FXML
    private TableColumn<?, ?> idColumnTT;

    @FXML
    private TableColumn<?, ?> firstNameColumnTT;

    @FXML
    private TableColumn<?, ?> timeColumnTT;


    // Input pour l'ajout des employés

    @FXML
    private TextField inputFirstName;

    @FXML
    private TextField inputLastName;

    @FXML
    private ComboBox<String> inputDepartment;

    // TODO : Voir si on peut pas mettre un sélecteur d'horloge plutôt qu'un TextField
    @FXML
    private TextField inputIn;

    @FXML
    private TextField inputOut;

    @FXML
    private TextField inputSalary;

    // Onglet infos détaillées
    @FXML
    private TableView<Employee> employeeDetailsTable;

    @FXML
    private Tab employeeDetailsTab;

    @FXML
    private TableColumn<Employee, String> idColumn;

    @FXML
    private TableColumn<Employee, String> firstNameColumn;

    @FXML
    private TableColumn<Employee, String> lastNameColumn;

    @FXML
    private TableColumn<Employee, String> departmentColumn;

    @FXML
    private TableColumn<Employee, Integer> salaryColumn;

    @FXML
    private TableColumn<Employee, LocalTime> startHourColumn;

    @FXML
    private TableColumn<Employee, LocalTime> endHourColumn;

    @FXML
    private Button saveChangesButton;

    private Employee selectedEmployee;

    private ManageCompany controller;

    public void setController(ManageCompany controller) {
        this.controller = controller;
    }

    public ManageCompany getController() {
        return controller;
    }


    @FXML
    public void initialize() {
        firstNameColumnEmployees.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumnEmployees.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        departementEmployees.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        inTimeEmployees.setCellValueFactory(new PropertyValueFactory<>("startHour"));
        outTimeEmployees.setCellValueFactory(new PropertyValueFactory<>("endHour"));


        ManageCompany controller = new ManageCompany(Company.deserializeCompany("timeTrackerApp/src/main/resources/data/company/Polytech.ser"),this);
        setController(controller);

        for (String departmentName : controller.getDepartments().keySet()) {
            inputDepartment.getItems().add(departmentName);
        }

        // DEBUG
        System.out.println(controller.getCompanyName()+" company loading...");
        System.out.println("Company object deserialized");
        Hashtable<String,Department> departementlist =  controller.getDepartments();
        System.out.println(departementlist);

        // Ajout de tous les employés dans la table
        for (Department department : controller.getDepartments().values()) {
            System.out.println(department);
            for (Employee employee : department.getEmployeesList().values()) {
                employeesTable.getItems().add(employee);
                System.out.println("Ajout de :"+employee.getFirstName());
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
            if (!newText.matches("\\d{2}:\\d{2}")) {
                // Si le nouveau texte ne correspond pas au format "HH:MM", désactivez le bouton addEmployeeButton
                addEmployeeButton.setDisable(true);
            } else {
                // Si le nouveau texte correspond au format "HH:MM", activez le bouton addEmployeeButton
                addEmployeeButton.setDisable(false);
            }
        });

        inputOut.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.matches("\\d{2}:\\d{2}")) {
                // Si le nouveau texte ne correspond pas au format "HH:MM", désactivez le bouton addEmployeeButton
                addEmployeeButton.setDisable(true);
            } else {
                // Si le nouveau texte correspond au format "HH:MM", activez le bouton addEmployeeButton
                addEmployeeButton.setDisable(false);
            }
        });

        inputSalary.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.matches("\\d+")) {
                // Si le nouveau texte n'est pas un nombre, désactivez le bouton addEmployeeButton
                addEmployeeButton.setDisable(true);
            } else {
                // Si le nouveau texte est un nombre, activez le bouton addEmployeeButton
                addEmployeeButton.setDisable(false);
            }
        });

        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab == employeeDetailsTab && selectedEmployee == null) {
                // Si l'utilisateur tente de sélectionner l'onglet des détails de l'employé sans avoir sélectionné un employé,
                // annulez le changement de sélection
                tabPane.getSelectionModel().select(oldTab);
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

    }

    @FXML
    protected void addEmployee() {
        String id = UUID.randomUUID().toString(); // Générer un ID unique pour chaque employé
        String firstName = inputFirstName.getText();
        String lastName = inputLastName.getText();
        String departmentName = (String) inputDepartment.getValue();
        LocalTime start_hour = LocalTime.parse(inputIn.getText());
        LocalTime end_hour = LocalTime.parse(inputOut.getText());
        LocalTime extra_hour = LocalTime.of(0, 0);
        String salaryString = inputSalary.getText();
        int salary = Integer.parseInt(salaryString);
        ClockingHistory clockingHistory = new ClockingHistory();

        // Ajoutez l'employé à l'objet Company
        String employeeId = controller.addEmployee(firstName, lastName, departmentName, salary, start_hour, end_hour);

        // Sérialisez l'objet Company
        controller.saveData(); // TODO delete, doublon

        // Ajoutez l'employé à la liste des items de la table
        employeesTable.getItems().add(controller.getEmployee(employeeId));
    }

    @FXML
    protected void deleteEmployee() {
        // Obtenir l'employé sélectionné
        Employee selectedEmployee = employeesTable.getSelectionModel().getSelectedItem();

        if (selectedEmployee != null) {
            // Supprimez l'employé de l'objet Company
            controller.deleteEmploye(selectedEmployee.getId());

            // Supprimez l'employé de la table
            employeesTable.getItems().remove(selectedEmployee);
        } else {
            // Aucun employé n'est sélectionné.
            System.out.println("Aucun employé sélectionné.");
        }
    }

    private void showEmployeeDetails(Employee employee) {
        // Mettez à jour l'employé sélectionné
        this.selectedEmployee = employee;

        // Remplissez le TableView avec les informations de l'employé
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        startHourColumn.setCellValueFactory(new PropertyValueFactory<>("startHour"));
        endHourColumn.setCellValueFactory(new PropertyValueFactory<>("endHour"));

        ObservableList<Employee> employeeDetails = FXCollections.observableArrayList();
        employeeDetails.add(employee);
        employeeDetailsTable.setItems(employeeDetails);


        // Ajoutez l'onglet au TabPane
        tabPane.getTabs().add(employeeDetailsTab);

        // Sélectionnez l'onglet
        tabPane.getSelectionModel().select(employeeDetailsTab);

    }

    private void saveChanges() {

        controller.updateData(selectedEmployee);
        employeesTable.refresh();
        employeeDetailsTable.refresh();
    }
}
