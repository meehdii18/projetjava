module org.example.timetrackerapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens javaProject.View to javafx.fxml;
    exports javaProject.View;

    opens javaProject.Controller to javafx.fxml;
}