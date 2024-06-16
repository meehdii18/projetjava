module org.example.timetrackerapp {
    requires javafx.controls;
    requires javafx.fxml;

    exports common.Model;

    exports timeTrackerApp.View;
    exports timeTrackerApp.Controller;
    exports timeTrackerApp.Model;

    exports  mainApp.View;
    exports mainApp.Controller;
    exports mainApp.Model;

    opens timeTrackerApp.View to javafx.fxml;

    opens mainApp.View to javafx.fxml;

}