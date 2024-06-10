package mainApp.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import mainApp.Model.ClockingHistory;
import mainApp.Model.Company;
import mainApp.Model.Employee;
import mainApp.Model.Department;
import mainApp.Model.StorageSerializer;

import java.io.File;
import java.time.LocalTime;
import java.util.Locale;
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
    private TableColumn<Employee,String> idColumnEmployees;

    @FXML
    private TableColumn<Employee, String> departementEmployees;

    @FXML
    private TableColumn<Employee, LocalTime> inTimeEmployees;

    @FXML
    private TableColumn<Employee, LocalTime> outTimeEmployees;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;



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
    private TextField inputDepartment;

    @FXML
    private TextField inputIn;

    @FXML
    private TextField inputOut;

    @FXML
    private TextField inputSalary;

    private Company company;


    @FXML
    public void initialize() {
        firstNameColumnEmployees.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumnEmployees.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        idColumnEmployees.setCellValueFactory(new PropertyValueFactory<>("id"));
        departementEmployees.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        inTimeEmployees.setCellValueFactory(new PropertyValueFactory<>("start_hour"));
        outTimeEmployees.setCellValueFactory(new PropertyValueFactory<>("end_hour"));

        company = Company.deserializeCompany("Polytech.ser");

        // Ajoutez tous les employés de l'objet Company à la table
        for (Department department : company.getDepartmentsList().values()) {
            for (Employee employee : department.getEmployeesList().values()) {
                employeesTable.getItems().add(employee);
            }
        }

        tabPane.getSelectionModel().select(0); // Sélectionne le premier onglet
        addEmployeeButton.setOnAction(event -> addEmployee());
        removeEmployeeButton.setOnAction(event -> deleteEmployee());
    }

    @FXML
    protected void addEmployee() {
        // TODO : Code pour ajouter un employé, il faut l'enregistrer dans les données sérialisées et rafraichir la vue

        String id = UUID.randomUUID().toString(); // Générer un ID unique pour chaque employé
        String firstName = inputFirstName.getText();
        String lastName = inputLastName.getText();
        String departmentName = inputDepartment.getText();
        LocalTime start_hour = LocalTime.parse(inputIn.getText());
        LocalTime end_hour = LocalTime.parse(inputOut.getText());
        LocalTime extra_hour = LocalTime.of(0, 0); // Vous pouvez modifier cela en fonction de vos besoins
        String salaryString = inputSalary.getText(); // Vous pouvez modifier cela en fonction de vos besoins
        int salary = Integer.valueOf(salaryString);
        ClockingHistory clockingHistory = new ClockingHistory();

        Employee employee = new Employee(id, firstName, lastName, departmentName, salary, start_hour, end_hour, extra_hour,clockingHistory);

        // Ajoutez l'employé à l'objet Company
        company.addEmployee(departmentName, employee);

        // Sérialisez l'objet Company
        company.serializeCompany();

        // Ajoutez l'employé à la liste des items de la table
        employeesTable.getItems().add(employee);
    }

    @FXML
    protected void deleteEmployee() {
        // Obtenir l'employé sélectionné
        Employee selectedEmployee = employeesTable.getSelectionModel().getSelectedItem();

        if (selectedEmployee != null) {
            // Supprimez l'employé de l'objet Company
            company.getDepartment(selectedEmployee.getDepartmentName()).getEmployeesList().remove(selectedEmployee.getId());

            // Sérialisez l'objet Company
            company.serializeCompany();

            // Supprimez l'employé de la table
            employeesTable.getItems().remove(selectedEmployee);
        } else {
            // Aucun employé n'est sélectionné
            System.out.println("Aucun employé sélectionné.");
        }
    }
}
