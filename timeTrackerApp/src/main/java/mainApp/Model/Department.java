package mainApp.Model;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Represents a department in an organization.
 * A department has a name and a list of employees.
 * Provides methods to get and set the department name, add an employee, delete an employee, get an employee by ID, and get the list of all employees.
 */
public class Department implements Serializable {

    /**
     * The name of the department.
     */
    private String departmentName;

    /**
     * The list of employees in the department.
     */
    private final Hashtable<String, Employee> employeesList = new Hashtable<>();

    /**
     * Constructs a new Department with the given name.
     *
     * @param name The name of the department.
     */
    public Department(String name) {
        departmentName = name;
    }

    /**
     * Retrieves the name of the department.
     *
     * @return The name of the department.
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * Sets the name of the department.
     *
     * @param newName The new name of the department.
     */
    public void setDepartmentName(String newName) {
        this.departmentName = newName;
    }

    /**
     * Adds an employee to the department.
     *
     * @param employee The employee to add.
     */
    public void addEmployee(Employee employee) {
        employeesList.put(employee.getId(), employee);
    }

    /**
     * Deletes an employee from the department.
     *
     * @param employeeId The ID of the employee to delete.
     */
    public void deleteEmployee(String employeeId) {
        employeesList.remove(employeeId);
    }

    /**
     * Retrieves an employee from the department by their ID.
     *
     * @param idEmployee The ID of the employee to retrieve.
     * @return The employee with the given ID, or null if no such employee exists.
     */
    public Employee getEmployee(String idEmployee) {
        return employeesList.get(idEmployee);
    }

    /**
     * Retrieves the list of all employees in the department.
     *
     * @return The list of all employees in the department.
     */
    public Hashtable<String, Employee> getEmployeesList() {
        return employeesList;
    }
}