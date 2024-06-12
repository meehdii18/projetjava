package mainApp.Controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import mainApp.Model.ClockingHistory;
import mainApp.Model.Company;
import mainApp.Model.Employee;
import mainApp.Model.Department;

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
    private ComboBox inputDepartment;

    // TODO : Voir si on peut pas mettre un selecteur d'horloge plutot qu'un TextField
    @FXML
    private TextField inputIn;

    @FXML
    private TextField inputOut;

    @FXML
    private TextField inputSalary;

    // Onglet infos détaillées
    @FXML
    private Tab employeeDetailsTab;

    private Employee selectedEmployee;

    private static Company company;

    public static void setCompany(Company company) {
        MainAppController.company = company;
    }


    @FXML
    public void initialize() {
        firstNameColumnEmployees.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumnEmployees.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        departementEmployees.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        inTimeEmployees.setCellValueFactory(new PropertyValueFactory<>("startHour"));
        outTimeEmployees.setCellValueFactory(new PropertyValueFactory<>("endHour"));


        // TODO : Régler le fait que les données ne sont pas bien sauvegardées / restaurés

        company.initializeDefaultDepartments();
        company.serializeCompany();

        for (String departmentName : company.getDepartmentsList().keySet()) {
            inputDepartment.getItems().add(departmentName);
        }
        System.out.println(company.getCompanyName()+" company loading...");
        System.out.println("Company object deserialized");
        Hashtable<String,Department> departementlist =  company.getDepartmentsList();
        System.out.println(departementlist);

        // Ajout de tout les employés dans la table
        for (Department department : company.getDepartmentsList().values()) {
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


        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab == employeeDetailsTab && selectedEmployee == null) {
                // Si l'utilisateur tente de sélectionner l'onglet des détails de l'employé sans avoir sélectionné un employé,
                // annulez le changement de sélection
                tabPane.getSelectionModel().select(oldTab);
            }
        });

        tabPane.getTabs().remove(employeeDetailsTab);

        tabPane.getSelectionModel().select(0); // Select the first tab
        addEmployeeButton.setOnAction(event -> addEmployee());
        removeEmployeeButton.setOnAction(event -> deleteEmployee());
    }

    @FXML
    protected void addEmployee() {
        // TODO : Code pour ajouter un employé, il faut l'enregistrer dans les données sérialisées et rafraichir la vue

        String id = UUID.randomUUID().toString(); // Générer un ID unique pour chaque employé
        String firstName = inputFirstName.getText();
        String lastName = inputLastName.getText();
        String departmentName = (String) inputDepartment.getValue();
        LocalTime start_hour = LocalTime.parse(inputIn.getText());
        LocalTime end_hour = LocalTime.parse(inputOut.getText());
        LocalTime extra_hour = LocalTime.of(0, 0); // Vous pouvez modifier cela en fonction de vos besoins
        String salaryString = inputSalary.getText(); // Vous pouvez modifier cela en fonction de vos besoins
        int salary = Integer.valueOf(salaryString);
        ClockingHistory clockingHistory = new ClockingHistory();

        Employee employee = new Employee(id, firstName, lastName, departmentName, salary, start_hour, end_hour, extra_hour,clockingHistory,null);

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

    private void showEmployeeDetails(Employee employee) {
        // Mettez à jour l'employé sélectionné
        this.selectedEmployee = employee;

        // Activez l'onglet
        employeeDetailsTab.setDisable(false);

        // Ajoutez l'onglet au TabPane
        tabPane.getTabs().add(employeeDetailsTab);

        // Sélectionnez l'onglet
        tabPane.getSelectionModel().select(employeeDetailsTab);


        // Désactivez l'onglet
        employeeDetailsTab.setDisable(true);
    }
}
