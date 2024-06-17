package mainApp.Controller;

import common.Model.DisplayClocking;
import mainApp.Model.Company;
import mainApp.Model.Department;
import mainApp.Model.Employee;
import mainApp.View.MainAppController;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Hashtable;

import static common.Model.Constants.QUIT;

/**
 * This class is responsible for managing the company's data and operations.
 * It communicates with the Company model and the MainAppController view.
 * It provides methods to add, delete, and update employees, get clocking data, and stop the server thread.
 */
 public class ManageCompany {

    public static final String LOCALHOST = "localhost";
    private final Company company;

    private final MainAppController view;

    /**
     * Constructor for the ManageCompany class.
     *
     * @param company The company model object.
     * @param view The MainAppController view object.
     */
    public ManageCompany(Company company, MainAppController view) {
        this.company = company;
        this.view = view;
    }

    /**
     * Returns the company model object.
     *
     * @return The company model object.
     */
    public Company getCompany() {
        return company;
    }

    /**
     * Returns the company name.
     *
     * @return The company name.
     */
    public String getCompanyName() {
        return company.getCompanyName();
    }

    /**
     * Returns the socket port number.
     *
     * @return The socket port number.
     */
    public int getSocket() {
        return company.getPort();
    }

    /**
     * Returns the list of departments in the company.
     *
     * @return The list of departments in the company.
     */
    public Hashtable<String, Department> getDepartments() {
        return company.getDepartmentsList();
    }

    /**
     * Adds an employee to the company.
     *
     * @param firstName The first name of the employee.
     * @param lastName The last name of the employee.
     * @param departmentName The department name of the employee.
     * @param salary The salary of the employee.
     * @param start_hour The start hour of the employee.
     * @param end_hour The end hour of the employee.
     * @return The ID of the added employee.
     */
    public String addEmployee(String firstName, String lastName, String departmentName, int salary, LocalTime start_hour, LocalTime end_hour) {
        return company.addEmployee(firstName, lastName, departmentName, salary, start_hour, end_hour);
    }

    /**
     * Deletes an employee from the company.
     *
     * @param employeeID The ID of the employee to be deleted.
     */
    public void deleteEmployee(String employeeID) {
        company.deleteEmployee(employeeID);
    }

    /**
     * Updates the data of an employee in the company.
     *
     * @param employee The employee object with updated data.
     */
    public void updateData(Employee employee) {
        company.updateEmployee(employee);
    }

    /**
     * Returns a copy of the employee object.
     *
     * @param employeeId The ID of the employee.
     * @return A copy of the employee object.
     */
    public Employee getEmployee(String employeeId) {
        return company.getCopyOfEmployee(employeeId);
    }

    /**
     * Returns all clocking data in the company.
     *
     * @return A set of all clocking data in the company.
     */
    public HashSet<DisplayClocking> getClocking() {
        return company.getAllClocking();
    }

    /**
     * Returns the clocking data of a specific employee.
     *
     * @param employeeId The ID of the employee.
     * @return A set of the clocking data of the employee.
     */
    public HashSet<DisplayClocking> getClocking(String employeeId) {
        return company.getClockingOfEmployee(employeeId);
    }

    /**
     * Stops the server thread.
     */
    public void stopServerThread() {
        try (Socket sock = new Socket(LOCALHOST, getSocket())) {

            ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());

            out.writeObject(QUIT);

            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


