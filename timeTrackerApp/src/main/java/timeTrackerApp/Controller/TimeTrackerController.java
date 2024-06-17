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

/**
 * This class is the controller for the TimeTracker application.
 * It handles the communication between the model and the view.
 */
public class TimeTrackerController {

    public static final int CONNECT_TIMEOUT = 50;
    private final TimeTracker model;
    private final MainScreenController mainView;

    /**
     * Constructor for the TimeTrackerController class.
     * @param model The model of the TimeTracker application.
     * @param mainView The main view of the TimeTracker application.
     */
    public TimeTrackerController(TimeTracker model, MainScreenController mainView) {
        this.model = model;
        this.mainView = mainView;
    }

    /**
     * Getter for the model.
     * @return The model of the TimeTracker application.
     */
    public TimeTracker getModel() {
        return model;
    }

    /**
     * This method creates a new clocking for an employee.
     * @param employeeId The ID of the employee.
     * @param now The current date.
     * @param approxTime The approximate time of the clocking.
     */
    public void newClocking(String employeeId, LocalDate now, LocalTime approxTime) {

        Clocking clocking = new Clocking(employeeId, now, approxTime);

        sendClocking(clocking);
    }

    /**
     * This method returns a hashtable of employees.
     * @return A hashtable of employees.
     */
    public Hashtable<String, String> getEmployees() {
        return model.getEmployees();
    }

    /**
     * This method returns the details of an employee.
     * @param employeeId The ID of the employee.
     * @return The details of the employee.
     */
    public String getEmployeeDetails(String employeeId) {
        return model.getEmployeeDetails(employeeId);
    }

    /**
     * This method fetches the distant employees and stores them in the model.
     */
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

    /**
     * This method gets the compact employees from the socket's input stream.
     * @param sock The socket to get the input stream from.
     * @return A hashset of compact employees.
     * @throws IOException If an I/O error occurs.
     */
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

    /**
     * This method sends a clocking to the server.
     * @param clocking The clocking to send.
     */
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