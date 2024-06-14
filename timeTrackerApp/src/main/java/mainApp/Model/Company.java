package mainApp.Model;

import common.Model.Clocking;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Hashtable;

public class Company implements Serializable {

    private String companyName;
    private final Hashtable<String, Department> departmentsList = new Hashtable<>();

    private final Hashtable<String, String> employeDepartment;

    public Company(String name){
        companyName = name;
        this.employeDepartment = new Hashtable<>();
    }

    public String getCompanyName(){
        return companyName;
    }

    public void setCompanyName(String newName){
        this.companyName = newName;
    }

    public Department getDepartment(String departmentName) {
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

    public String addEmployee(String firstName, String lastName, String departmentName, int salary, LocalTime start_hour, LocalTime end_hour) {
        Department department = getDepartment(departmentName);
        if (department == null) { // TODO : changer ça
            department = new Department(departmentName);
            departmentsList.put(departmentName, department);
        }

        Employee employee = new Employee(firstName, lastName, departmentName, salary, start_hour, end_hour);

        department.addEmploye(employee);

        department.getEmployeesList().put(employee.getId(), employee);
        employeDepartment.put(employee.getId(),departmentName);
        serializeCompany();

        System.out.println(employee.getId());

        return employee.getId();
    }

    public void deleteEmployee(String employeeId) {
        Department department = getDepartment(findDepartmentOfEmployee(employeeId));

        department.deleteEmploye(employeeId);

        serializeCompany();
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

    public String findDepartmentOfEmployee(String employeId) {
        return employeDepartment.get(employeId);
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