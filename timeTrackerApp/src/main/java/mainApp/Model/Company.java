package mainApp.Model;

import java.io.*;
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

    public void addEmployee(String departmentName, Employee employee) {
        Department department = getDepartment(departmentName);
        if (department == null) {
            department = new Department(departmentName);
            departmentsList.put(departmentName, department);
        }
        department.getEmployeesList().put(employee.getId(), employee);
        employeDepartment.put(employee.getId(),departmentName);
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
                i.printStackTrace();
            }
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
    }

    public String findDepartmentOfEmployee(String idEmploye) {
        return employeDepartment.get(idEmploye);
    }
}