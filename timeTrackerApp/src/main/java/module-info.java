module org.example.timetrackerapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.timetrackerapp to javafx.fxml;
    exports org.example.timetrackerapp;
}