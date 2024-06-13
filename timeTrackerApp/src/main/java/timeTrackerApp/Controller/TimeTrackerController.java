package timeTrackerApp.Controller;

import common.Model.Clocking;
import timeTrackerApp.Model.TimeTracker;
import timeTrackerApp.View.MainScreenController;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;

public class TimeTrackerController {

    private final TimeTracker model;

    private final MainScreenController mainView;

    public TimeTrackerController(TimeTracker model, MainScreenController mainView) {
        this.model = model;
        this.mainView = mainView;
    }

    public TimeTracker getModel() {
        return model;
    }

    public void newClocking(String employeeId, LocalDate now, LocalTime approxTime) {

        Clocking clocking = new Clocking(employeeId, now, approxTime);

        sendClocking(clocking);
    }

    private void sendClocking(Clocking clocking) {

        try (Socket sock = new Socket(model.getAddress(), model.getSocket())) {

            ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());

            out.writeObject(clocking);

            out.flush();

        } catch (IOException e) {
            System.out.println(e + "ici");
        }

        // TODO : Utiliser clocking en le stockant localement (temporairement) puis en l'envoyant via réseau à la mainApp
    }
}
