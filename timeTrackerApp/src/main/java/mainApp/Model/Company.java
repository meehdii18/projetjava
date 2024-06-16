package mainApp.Model;

import common.Model.Clocking;
import common.Model.CompactEmployee;
import common.Model.DisplayClocking;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.AbstractSet;
import java.util.HashSet;
import java.util.Hashtable;

public class Company implements Serializable {

    private String companyName;
    private final Hashtable<String, Department> departmentsList = new Hashtable<>();

    private final Hashtable<String, String> employeeDepartment;

    private int port;

    public Company(String name){
        companyName = name;
        this.employeeDepartment = new Hashtable<>();
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public String getCompanyName(){
        return companyName;
    }

    public void setCompanyName(String newName){
        this.companyName = newName;
    }

    public Department getDepartment(String departmentName) {

        if(departmentName == null) {
            throw new RuntimeException("Non-existing department");
        }

        return departmentsList.get(departmentName);
    }

    public Hashtable<String,Department> getDepartmentsList(){
        return departmentsList;
    }

    public void addDepartment(String departmentName) {
        if (getDepartment(departmentName) == null) {
            departmentsList.put(departmentName, new Department(departmentName));
        }
        else {
            System.out.println("The department named '" + departmentName + "'already exists.");
        }
    }

    public HashSet<Employee> getAllEmployees() {

        HashSet<Employee> employeesList = new HashSet<>();

        for (Department department : getDepartmentsList().values()) {
            for (Employee employee : department.getEmployeesList().values()) {
                employeesList.add(employee.clone());
            }
        }

        return employeesList;
    }

    public String addEmployee(String firstName, String lastName, String departmentName, int salary, LocalTime start_hour, LocalTime end_hour) {
        Department department = getDepartment(departmentName);

        Employee employee = new Employee(firstName, lastName, departmentName, salary, start_hour, end_hour);

        department.addEmployee(employee);

        department.getEmployeesList().put(employee.getId(), employee);
        employeeDepartment.put(employee.getId(),departmentName);
        serializeCompany();

        return employee.getId();
    }

    public void deleteEmployee(String employeeId) {
        Department department = getDepartment(findDepartmentOfEmployee(employeeId));

        department.deleteEmployee(employeeId);

        serializeCompany();
    }

    public HashSet<DisplayClocking> getAllClocking() {

        HashSet<DisplayClocking> clockingHashSet = new HashSet<>();

        for (Employee employee : getAllEmployees()) {
            clockingHashSet.addAll(getClockingOfEmployee(employee.getId()));
        }

        return clockingHashSet;
    }

    public HashSet<DisplayClocking> getClockingOfEmployee(String employeeId) {

        HashSet<DisplayClocking> clockingHashSet = new HashSet<>();

        Employee employee = getEmployee(employeeId);

        ClockingHistory clockingHistory = employee.getClockingHistory();

        for (LocalDate date : clockingHistory.getDaysClocked()) { // TODO : change employeeId by publicId
            clockingHashSet.add(new DisplayClocking(employeeId, employee.getFirstName(), employee.getLastName(), date, clockingHistory.queryClockIn(date), "in"));
            if(clockingHistory.queryClockOut(date) != null) {
                clockingHashSet.add(new DisplayClocking(employeeId, employee.getFirstName(), employee.getLastName(), date, clockingHistory.queryClockOut(date), "out"));
            }
        }

        return clockingHashSet;
    }

    public HashSet<CompactEmployee> sendLocalEmployeeToDistant() {

        HashSet<CompactEmployee> compactEmployeeHashSet = new HashSet<>();

        for (Employee employee : getAllEmployees()) {
            CompactEmployee compactEmployee = new CompactEmployee(employee.getId(), employee.getFirstName(), employee.getLastName(), true);

            compactEmployeeHashSet.add(compactEmployee);
        }

        return compactEmployeeHashSet;
    }

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
            e.printStackTrace();
        }
    }

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
                System.out.println(i.toString());
            }
            company = new Company("Polytech"); // TODO : repasser ici et gérer correctement les erreurs d'import
            company.initializeDefaultDepartments();
        } catch (ClassNotFoundException c) {
            System.out.println("Company class not found");
            c.printStackTrace();
        }
        return company;
    }

    public void initializeDefaultDepartments() {
        String[] defaultDepartments = {"Human Ressources", "Production", "Information Technology", "Executive"};

        for (String departmentName : defaultDepartments) {
            if (getDepartment(departmentName) == null) {
                addDepartment(departmentName);
            }
        }

        // TODO : peut être sérialiser ici
    }

    public void addClockingToEmployee(String employeeId, LocalDate date, LocalTime time) {

        getEmployee(employeeId).addClocking(date, time);

    }

    public String findDepartmentOfEmployee(String employeeId) {
        return employeeDepartment.get(employeeId);
    }

    public Employee getEmployee(String employeeId) {
        Department department = getDepartment(findDepartmentOfEmployee(employeeId));

        return department.getEmployee(employeeId);
    }

    public Employee getCopyOfEmployee(String employeeId) {
        Department department = getDepartment(findDepartmentOfEmployee(employeeId));

        return department.getEmployee(employeeId).clone();
    }

    public void updateEmployee(Employee employee) {
        Employee old = getEmployee(employee.getId());

        System.out.println(old.getDetails());

        old = employee; // TODO : retoucher à ça pour être sur
    }
}