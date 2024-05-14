package javaProject.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.Locale;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private Label dateText;

    @FXML
    private Label timeText;

    @FXML
    public void initialize(){
        // Le texte est mis à jour pour afficher la date actuelle de l'ordinateur
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d", Locale.ENGLISH);
        dateText.setText(LocalDate.now().format(dateFormatter));

        // Le texte est mis à jour chaque seconde pour afficher l'heure
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0), event -> timeText.setText(LocalTime.now().format(timeFormatter))),
                new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}