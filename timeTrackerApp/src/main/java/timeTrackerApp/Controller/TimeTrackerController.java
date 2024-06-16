package timeTrackerApp.Controller;

import common.Model.Clocking;
import common.Model.CompactEmployee;
import timeTrackerApp.Model.TimeTracker;
import timeTrackerApp.View.MainScreenController;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.Hashtable;

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

    public Hashtable<String, String> getEmployees() {
        return model.getEmployees();
    }

    public String getEmployeeDetails(String employeeId) {
        return model.getEmployeeDetails(employeeId);
    }

    public void fetchDistantEmployees() {

        try (Socket sock = new Socket(model.getAddress(), model.getSocket())) {

            ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());

            out.writeObject("fetch"); // TODO : change message by constant

            out.flush();

            ObjectInputStream in = new ObjectInputStream(sock.getInputStream());

            boolean streamNotEmpty = true;

            HashSet<CompactEmployee> compactEmployeeHashSet = new HashSet<>();

            while (streamNotEmpty) {

                try {

                    Object obj = in .readObject();

                    System.out.println(obj);

                    CompactEmployee employee = (CompactEmployee) obj;

                    compactEmployeeHashSet.add(employee);

                } catch (EOFException eofException) {
                    System.out.println("close");
                    streamNotEmpty = false;
                } catch (ClassNotFoundException classNotFoundException) {
                    throw new RuntimeException(classNotFoundException);
                }
            }

            model.store(compactEmployeeHashSet);

            // TODO : gérer exceptions

        } catch (IOException e) {

            System.out.println(e + " : Verify the network parameters or try again later");

        }
    }

    private void sendClocking(Clocking clocking) {

        try (Socket sock = new Socket(model.getAddress(), model.getSocket())) {

            ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());

            boolean existsNotSendClocking = true;

            while (existsNotSendClocking) {

                try {

                    Clocking ancientClocking = model.getNotSendClocking();

                    out.writeObject(ancientClocking);

                    out.flush();

                    System.out.println("ancient clocking sent : " + ancientClocking);

                } catch (EmptyStackException ex) {

                    existsNotSendClocking = false;

                }

            }

            out.writeObject(clocking);

            out.flush();

            System.out.println("current clocking sent : " + clocking);

        } catch (IOException e) {

            System.out.println(e + " : The ready-to-send Clocking is stored locally and will be send when connexion is possible.");

            model.addNotSendClocking(clocking);

        }

        // TODO : Utiliser clocking en le stockant localement (temporairement) puis en l'envoyant via réseau à la mainApp
    }
}
