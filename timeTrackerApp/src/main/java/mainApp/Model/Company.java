package mainApp.Model;

import java.io.*;
import java.util.Hashtable;

public class Company implements Serializable {

    private String companyName;

    private final Hashtable<String, Department> departmentsList = new Hashtable<>();

    public Company(String name){
        companyName = name;
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
        if (department != null) {
            department.getEmployeesList().put(employee.getId(), employee);
            serializeCompany();
        } else {
            System.out.println("The department named '" + departmentName + "' does not exist.");
        }
    }

    public void serializeCompany() {
        try {
            File file = new File("timeTrackerApp/src/main/resources/data/company/" + companyName + ".ser");
            file.getParentFile().mkdirs(); // Create the directory if it does not exist
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

    public static Company deserializeCompany(String fileName) {
        Company company = null;
        File file = new File("timeTrackerApp/src/main/resources/data/company/" + fileName);
        if (file.exists()) {
            try (FileInputStream fileIn = new FileInputStream(file);
                 ObjectInputStream in = new ObjectInputStream(fileIn)) {
                company = (Company) in.readObject();
                System.out.println("The Company object has been deserialized from " + fileName);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            company = new Company("Polytech");
            company.serializeCompany();
        }
        return company;
    }
}