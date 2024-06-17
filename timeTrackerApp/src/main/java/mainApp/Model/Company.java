package mainApp.Model;

import common.Model.CompactEmployee;
import common.Model.DisplayClocking;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Hashtable;

/**
 * Represents a company in an organization.
 * A company has a name, a list of departments, and a port for server communication.
 * Provides methods to get and set the company name, add a department, get a department by name, get the list of all departments,
 * add an employee, delete an employee, get all employees, get all clocking, get clocking of an employee, serialize the company,
 * deserialize the company, initialize default departments, initialize default employees, add clocking to an employee,
 * find the department of an employee, get an employee by ID, get a copy of an employee, and update an employee.
 */
public class Company implements Serializable {
    /**
     * The name of the company.
     */
    private String companyName;

    /**
     * The list of departments in the company.
     */
    private final Hashtable<String, Department> departmentsList = new Hashtable<>();

    /**
     * The hashtable mapping employee IDs to their department names.
     */
    private final Hashtable<String, String> employeeDepartment;

    /**
     * The port for server communication.
     */
    private int port;

    /**
     * Constructs a new Company with the given name.
     *
     * @param name The name of the company.
     */
    public Company(String name) {
        companyName = name;
        this.employeeDepartment = new Hashtable<>();
    }

    /**
     * Sets the port for server communication.
     *
     * @param port The port for server communication.
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Retrieves the port for server communication.
     *
     * @return The port for server communication.
     */
    public int getPort() {
        return port;
    }

    /**
     * Retrieves the name of the company.
     *
     * @return The name of the company.
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets the name of the company.
     *
     * @param newName The new name of the company.
     */
    public void setCompanyName(String newName) {
        this.companyName = newName;
    }

    /**
     * Retrieves a department from the company by its name.
     *
     * @param departmentName The name of the department to retrieve.
     * @return The department with the given name.
     * @throws RuntimeException if the department does not exist.
     */
    public Department getDepartment(String departmentName) {

        if (departmentName == null) {
            throw new RuntimeException("Non-existing department");
        }

        return departmentsList.get(departmentName);
    }

    /**
     * Retrieves the list of all departments in the company.
     *
     * @return The list of all departments in the company.
     */
    public Hashtable<String, Department> getDepartmentsList() {
        return departmentsList;
    }

    /**
     * Adds a department to the company.
     *
     * @param departmentName The name of the department to add.
     */
    public void addDepartment(String departmentName) {
        if (getDepartment(departmentName) == null) {
            departmentsList.put(departmentName, new Department(departmentName));
        } else {
            System.out.println("The department named '" + departmentName + "'already exists.");
        }
    }

    /**
     * Retrieves all employees in the company.
     *
     * @return A set of all employees in the company.
     */
    public HashSet<Employee> getAllEmployees() {

        HashSet<Employee> employeesList = new HashSet<>();

        for (Department department : getDepartmentsList().values()) {
            for (Employee employee : department.getEmployeesList().values()) {
                employeesList.add(employee.clone());
            }
        }

        return employeesList;
    }

    /**
     * Adds an employee to a department in the company.
     *
     * @param firstName      The first name of the employee.
     * @param lastName       The last name of the employee.
     * @param departmentName The name of the department to which the employee is to be added.
     * @param salary         The salary of the employee.
     * @param start_hour     The start hour of the employee's work day.
     * @param end_hour       The end hour of the employee's work day.
     * @return The ID of the newly added employee.
     */
    public String addEmployee(String firstName, String lastName, String departmentName, int salary, LocalTime start_hour, LocalTime end_hour) {
        Department department = getDepartment(departmentName);

        Employee employee = new Employee(firstName, lastName, departmentName, salary, start_hour, end_hour);

        department.addEmployee(employee);

        department.getEmployeesList().put(employee.getId(), employee);
        employeeDepartment.put(employee.getId(), departmentName);
        serializeCompany();

        return employee.getId();
    }

    /**
     * Deletes an employee from the company.
     *
     * @param employeeId The ID of the employee to delete.
     */
    public void deleteEmployee(String employeeId) {
        Department department = getDepartment(findDepartmentOfEmployee(employeeId));

        department.deleteEmployee(employeeId);

        serializeCompany();
    }

    /**
     * Retrieves all clocking events in the company.
     *
     * @return A set of all clocking events in the company.
     */
    public HashSet<DisplayClocking> getAllClocking() {

        HashSet<DisplayClocking> clockingHashSet = new HashSet<>();

        for (Employee employee : getAllEmployees()) {
            clockingHashSet.addAll(getClockingOfEmployee(employee.getId()));
        }

        return clockingHashSet;
    }

    /**
     * Retrieves the clocking events of a specific employee.
     *
     * @param employeeId The ID of the employee whose clocking events are to be retrieved.
     * @return A set of the clocking events of the specified employee.
     */
    public HashSet<DisplayClocking> getClockingOfEmployee(String employeeId) {

        HashSet<DisplayClocking> clockingHashSet = new HashSet<>();

        Employee employee = getEmployee(employeeId);

        ClockingHistory clockingHistory = employee.getClockingHistory();

        for (LocalDate date : clockingHistory.getDaysClocked()) {
            clockingHashSet.add(new DisplayClocking(employeeId, employee.getFirstName(), employee.getLastName(), date, clockingHistory.queryClockIn(date), "in"));
            if (clockingHistory.queryClockOut(date) != null) {
                clockingHashSet.add(new DisplayClocking(employeeId, employee.getFirstName(), employee.getLastName(), date, clockingHistory.queryClockOut(date), "out"));
            }
        }

        return clockingHashSet;
    }

    /**
     * Prepare local employee data for sending to a distant server.
     *
     * @return A set of compact employee data to be sent to a distant server.
     */
    public HashSet<CompactEmployee> sendLocalEmployeeToDistant() {

        HashSet<CompactEmployee> compactEmployeeHashSet = new HashSet<>();

        for (Employee employee : getAllEmployees()) {
            CompactEmployee compactEmployee = new CompactEmployee(employee.getId(), employee.getFirstName(), employee.getLastName());

            compactEmployeeHashSet.add(compactEmployee);
        }

        return compactEmployeeHashSet;
    }

    /**
     * Serializes the company to a file.
     */
    public void serializeCompany() {
        try {
            File file = new File("timeTrackerApp/src/main/resources/data/company/" + companyName + ".ser");
            file.getParentFile().mkdirs();
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            System.out.println("The Company object has been serialized and written to " + companyName + ".ser");
        } catch (IOException e) {
            System.out.println(e + " : Unable to serialize company " + companyName);
        }
    }

    /**
     * Deserializes a company from a file.
     *
     * @param filename The name of the file from which to deserialize the company.
     * @return The deserialized company.
     */
    public static Company deserializeCompany(String filename) {
        Company company = null;
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            company = (Company) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            if (i instanceof FileNotFoundException) {
                System.out.println("File not found");
            } else {
                System.out.println(i + " : Unable to deserialize company");
            }
            company = new Company("Polytech");
            company.initializeDefaultDepartments();
            company.initializeDefaultEmployees();
        } catch (ClassNotFoundException c) {
            System.out.println("Company class not found");
        }
        return company;
    }

    /**
     * Initializes default departments in the company.
     */
    public void initializeDefaultDepartments() {
        String[] defaultDepartments = {"Human Ressources", "Production", "Information Technology", "Executive"};

        for (String departmentName : defaultDepartments) {
            if (getDepartment(departmentName) == null) {
                addDepartment(departmentName);
            }
        }
        serializeCompany();
    }

    /**
     * Initializes default employees in the company.
     */
    public void initializeDefaultEmployees() {
        addEmployee("jean", "bol", "Human Ressources", 2300, LocalTime.of(4, 0), LocalTime.of(12, 0));
        addEmployee("pierre", "gris", "Production", 2700, LocalTime.of(6, 0), LocalTime.of(12, 0));
        addEmployee("marc", "cerf", "Information Technology", 3000, LocalTime.of(9, 0), LocalTime.of(15, 0));
        addEmployee("luc", "bon", "Executive", 4200, LocalTime.of(7, 0), LocalTime.of(18, 0));
    }

    /**
     * Adds a clocking event to an employee.
     *
     * @param employeeId The ID of the employee to whom the clocking event is to be added.
     * @param date       The date of the clocking event.
     * @param time       The time of the clocking event.
     */
    public void addClockingToEmployee(String employeeId, LocalDate date, LocalTime time) {

        getEmployee(employeeId).addClocking(date, time);

    }

    /**
     * Finds the department of a specific employee.
     *
     * @param employeeId The ID of the employee whose department is to be found.
     * @return The name of the department of the specified employee.
     */
    public String findDepartmentOfEmployee(String employeeId) {
        return employeeDepartment.get(employeeId);
    }

    /**
     * Retrieves an employee from the company by their ID.
     *
     * @param employeeId The ID of the employee to retrieve.
     * @return The employee with the given ID, or null if no such employee exists.
     */
    public Employee getEmployee(String employeeId) {
        Department department = getDepartment(findDepartmentOfEmployee(employeeId));

        return department.getEmployee(employeeId);
    }

    /**
     * Retrieves a copy of an employee from the company by their ID.
     *
     * @param employeeId The ID of the employee to retrieve.
     * @return A copy of the employee with the given ID, or null if no such employee exists.
     */
    public Employee getCopyOfEmployee(String employeeId) {
        Department department = getDepartment(findDepartmentOfEmployee(employeeId));

        return department.getEmployee(employeeId).clone();
    }

    /**
     * Updates an employee in the company.
     *
     * @param employee The updated employee.
     */
    public void updateEmployee(Employee employee) {
        Employee old = getEmployee(employee.getId());

        old = employee;

        serializeCompany();
    }
}