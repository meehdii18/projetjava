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
    private TableColumn<?, ?> nameColumnEmployees;

    @FXML
    private TableColumn<?,?> idColumnEmployees;

    @FXML
    private TableColumn<?,?> departementEmployees;

    @FXML
    private TableColumn<?,?> planningEmployees;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;



    // Time tracking tab
    // TODO : changer tout les '?' avec le bon type de données
    @FXML
    private TableView<?> timeTrakingTable;

    @FXML
    private TableColumn<?, ?> nameColumnTT;

    @FXML
    private TableColumn<?, ?> idColumnTT;

    @FXML
    private TableColumn<?, ?> timeInColumnTT;

    @FXML
    private TableColumn<?, ?> timeOutColumnTT;


    @FXML
    public void initialize() {
        tabPane.getSelectionModel().select(0); // Sélectionne le premier onglet

        // Lien entre l'action d'appuyer sur le bouton et la fonction associées
        addButton.setOnAction(event -> addEmployee());
        deleteButton.setOnAction(event -> deleteEmployee());

        // Setup initial data and bindings for the TableView and its columns if needed
        // TODO : initialiser les données dans les deux TableView
    }

    private void addEmployee() {
        // TODO : Code pour ajouter un employé
    }

    private void deleteEmployee() {
        // TODO : Code pour supprimer un employé
    }

}
