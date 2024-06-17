package timeTrackerApp.Controller;

import common.Model.Clocking;
import common.Model.CompactEmployee;
import timeTrackerApp.Model.TimeTracker;
import timeTrackerApp.View.MainScreenController;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.Hashtable;

import static common.Model.Constants.FETCH;

public class TimeTrackerController {

    public static final int CONNECT_TIMEOUT = 50;
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

        try (Socket sock = new Socket()) {

            sock.connect(new InetSocketAddress(model.getAddress(), model.getSocket()), CONNECT_TIMEOUT);

            ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());

            out.writeObject(FETCH);

            out.flush();

            HashSet<CompactEmployee> compactEmployeeHashSet = getCompactEmployees(sock);

            model.store(compactEmployeeHashSet);

        } catch (IOException e) {

            mainView.display("Verify the network parameters or try again later");

            System.out.println(e + " : Verify the network parameters or try again later");

        }
    }

    private static HashSet<CompactEmployee> getCompactEmployees(Socket sock) throws IOException {
        ObjectInputStream in = new ObjectInputStream(sock.getInputStream());

        boolean streamNotEmpty = true;

        HashSet<CompactEmployee> compactEmployeeHashSet = new HashSet<>();

        while (streamNotEmpty) {

            try {

                Object obj = in .readObject();

                CompactEmployee employee = (CompactEmployee) obj;

                compactEmployeeHashSet.add(employee);

            } catch (EOFException eofException) {
                streamNotEmpty = false;
            } catch (ClassNotFoundException classNotFoundException) {
                throw new RuntimeException(classNotFoundException);
            }
        }
        return compactEmployeeHashSet;
    }

    private void sendClocking(Clocking clocking) {
        
        try (Socket sock = new Socket()){

            sock.connect(new InetSocketAddress(model.getAddress(), model.getSocket()), CONNECT_TIMEOUT);

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

            mainView.display("The ready-to-send Clocking is stored locally and will be send when connexion is possible.");

            model.addNotSendClocking(clocking);

        }
    }
}
