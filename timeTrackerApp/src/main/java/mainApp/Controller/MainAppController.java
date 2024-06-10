package mainApp.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainAppController {
    @FXML
    private TabPane tabPane;

    @FXML
    private Label ipLabel;

    @FXML
    private Label socketLabel;

    @FXML
    private TextField searchEmployeeField;

    @FXML
    private Button addEmployeeButton;

    @FXML
    private Button removeEmployeeButton;

    // Employees tab
    // TODO : changer tout les '?' avec le bon type de données
    @FXML
    private TableView<?> employeesTable;

    @FXML
    private TableColumn<?, ?> firstNameColumnEmployees;

    @FXML
    private TableColumn<?, ?> lastNameColumnEmployees;

    @FXML
    private TableColumn<?,?> idColumnEmployees;

    @FXML
    private TableColumn<?,?> departementEmployees;

    @FXML
    private TableColumn<?,?> planningEmployees;

    @FXML
    private TableColumn<?,?> inTimeEmlployees;

    @FXML
    private TableColumn<?,?> outTimeEmlployees;

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


    @FXML
    public void initialize() {
        tabPane.getSelectionModel().select(0); // Sélectionne le premier onglet

        // TODO : initialiser les données dans les deux TableView à partir des données sérialisées
    }

    @FXML
    protected void addEmployee() {
        // TODO : Code pour ajouter un employé, il faut l'enregistrer dans les données sérialisées et rafraichir la vue
    }

    @FXML
    protected void deleteEmployee() {
        // TODO : Code pour supprimer un employé, il faut le supprimer dans les données sérialisées et rafraichir la vue
    }

}
