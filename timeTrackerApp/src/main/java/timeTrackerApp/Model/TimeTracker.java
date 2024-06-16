package timeTrackerApp.Model;

import common.Model.Clocking;
import common.Model.CompactEmployee;
import mainApp.Model.Company;
import mainApp.Model.Department;
import mainApp.Model.Employee;

import java.io.*;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Stack;

public class TimeTracker implements Serializable{

    private static final String DEFAULT_ADDRESS = "localhost";

    private static final int DEFAULT_SOCKET = 1234;

    private String address;

    private int socket;

    private final Stack<Clocking> notSendClockings;

    private final Hashtable<String,String> employeeList;

    public TimeTracker() {
        this.address = DEFAULT_ADDRESS;
        this.socket = DEFAULT_SOCKET;
        this.notSendClockings = new Stack<>();
        this.employeeList = new Hashtable<>();

        // TODO : change default values (by constants ?)
    }

    private void debugImportEmployees() { // TODO : à dégager quand récupération des employés distants fonctionnelle

        Company comp = Company.deserializeCompany("timeTrackerApp/src/main/resources/data/company/Polytech.ser");

        for (Department dep : comp.getDepartmentsList().values()) {
            for (Employee employee : dep.getEmployeesList().values()) {
                CompactEmployee emp = new CompactEmployee(employee.getId(), employee.getFirstName(), employee.getLastName(), true);
                store(emp);
            }
        }

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;

        serializeLocalData();
    }

    public int getSocket() {
        return socket;
    }

    public void setSocket(int socket) {

        this.socket = socket;

        serializeLocalData();
    }

    public void addNotSendClocking(Clocking clocking) {

        notSendClockings.push(clocking);

        serializeLocalData();

    }

    public Clocking getNotSendClocking() {

        Clocking lastClocking = notSendClockings.pop();

        serializeLocalData();

        return lastClocking;
    }

    public void store(HashSet<CompactEmployee> compactEmployeeHashSet) {

        employeeList.clear();

        for (CompactEmployee employee : compactEmployeeHashSet) {
            store(employee);
        }
    }

    public void store(CompactEmployee employee) {

        if (employee.toAdd()) {
            employeeList.put(employee.employeeId(), employee.firstName() + " " + employee.lastName()); // TODO : à test
        } else {
            employeeList.remove(employee.employeeId());
        }

        serializeLocalData();
    }

    public Hashtable<String, String> getEmployees() {
        return employeeList;
    }

    public String getEmployeeDetails(String employeId) {
        return employeeList.get(employeId);
    }

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
            } else {
                System.out.println(i.toString()); // TODO : repasser ici et gérer correctement les erreurs d'import
            }
            timeTracker = new TimeTracker();
        } catch (ClassNotFoundException c) {
            System.out.println("TimeTracker class not found");
            c.printStackTrace();
        }

        timeTracker.debugImportEmployees();

        return timeTracker;
    }
}
