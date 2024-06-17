package timeTrackerApp.Model;

import common.Model.Clocking;
import common.Model.CompactEmployee;

import java.io.*;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Stack;

/**
 * Represents a time tracker in an organization.
 * A time tracker has an address, a socket for server communication, a stack of not sent clocking events, and a list of employees.
 * Provides methods to get and set the address, get and set the socket, add a not sent clocking event, get a not sent clocking event,
 * store a set of compact employee data, store a compact employee, get the list of all employees, get the details of an employee,
 * serialize the time tracker to a file, and deserialize a time tracker from a file.
 */
public class TimeTracker implements Serializable {

    /**
     * The default address for server communication.
     */
    private static final String DEFAULT_ADDRESS = "localhost";

    /**
     * The default socket for server communication.
     */
    private static final int DEFAULT_SOCKET = 1234;

    /**
     * The address for server communication.
     */
    private String address;

    /**
     * The socket for server communication.
     */
    private int socket;

    /**
     * The stack of not sent clocking events.
     */
    private final Stack<Clocking> notSendClocking;

    /**
     * The list of employees in the organization.
     */
    private final Hashtable<String, String> employeeList;

    /**
     * Constructs a new TimeTracker with the default address and socket.
     */
    public TimeTracker() {
        this.address = DEFAULT_ADDRESS;
        this.socket = DEFAULT_SOCKET;
        this.notSendClocking = new Stack<>();
        this.employeeList = new Hashtable<>();
    }

    /**
     * Retrieves the address for server communication.
     *
     * @return The address for server communication.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address for server communication.
     *
     * @param address The new address for server communication.
     */
    public void setAddress(String address) {
        this.address = address;

        serializeLocalData();
    }

    /**
     * Retrieves the socket for server communication.
     *
     * @return The socket for server communication.
     */
    public int getSocket() {
        return socket;
    }

    /**
     * Sets the socket for server communication.
     *
     * @param socket The new socket for server communication.
     */
    public void setSocket(int socket) {

        this.socket = socket;

        serializeLocalData();
    }

    /**
     * Adds a not sent clocking event to the stack.
     *
     * @param clocking The clocking event to add.
     */
    public void addNotSendClocking(Clocking clocking) {

        notSendClocking.push(clocking);

        serializeLocalData();

    }

    /**
     * Retrieves and removes the last not sent clocking event from the stack.
     *
     * @return The last not sent clocking event.
     */
    public Clocking getNotSendClocking() {

        Clocking lastClocking = notSendClocking.pop();

        serializeLocalData();

        return lastClocking;
    }

    /**
     * Stores a set of compact employee data in the list of employees.
     *
     * @param compactEmployeeHashSet The set of compact employee data to store.
     */
    public void store(HashSet<CompactEmployee> compactEmployeeHashSet) {

        employeeList.clear();

        for (CompactEmployee employee : compactEmployeeHashSet) {
            store(employee);
        }
    }

    /**
     * Stores a compact employee in the list of employees.
     *
     * @param employee The compact employee to store.
     */
    public void store(CompactEmployee employee) {

        employeeList.put(employee.employeeId(), employee.firstName() + " " + employee.lastName());

        serializeLocalData();
    }

    /**
     * Retrieves the list of all employees in the organization.
     *
     * @return The list of all employees.
     */
    public Hashtable<String, String> getEmployees() {
        return employeeList;
    }

    /**
     * Retrieves the details of an employee by their ID.
     *
     * @param employeeId The ID of the employee to retrieve.
     * @return The details of the employee with the given ID, or null if no such employee exists.
     */
    public String getEmployeeDetails(String employeeId) {
        return employeeList.get(employeeId);
    }

    /**
     * Serializes the time tracker to a file.
     */
    public void serializeLocalData() {
        try {
            File file = new File("timeTrackerApp/src/main/resources/data/timeTracker/localData.ser");
            file.getParentFile().mkdirs();
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            System.out.println("The TimeTracker object has been serialized and written to localData.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserializes a time tracker from a file.
     *
     * @param filename The name of the file from which to deserialize the time tracker.
     * @return The deserialized time tracker.
     */
    public static TimeTracker deserializeLocalData(String filename) {
        TimeTracker timeTracker = null;
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            timeTracker = (TimeTracker) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            if (i instanceof FileNotFoundException) {
                System.out.println("File not found");
            }
            timeTracker = new TimeTracker();
        } catch (ClassNotFoundException classNotFoundException) {
            System.out.println("TimeTracker class not found");
        }

        return timeTracker;
    }
}
