module org.example.timetrackerapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens timeTrackerApp.View to javafx.fxml;
    exports timeTrackerApp.View;
    opens timeTrackerApp.Controller to javafx.fxml;

    opens mainApp.View to javafx.fxml;
    exports mainApp.View;
    opens mainApp.Controller to javafx.fxml;
}