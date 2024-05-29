package mainApp.Model;

import java.util.Hashtable;

public class Company {

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

    public void addDepartment(String departmentName) {
        if (getDepartment(departmentName) == null) {
            departmentsList.put(departmentName, new Department(departmentName));
        }
        else {
            // TODO : voir pour remplacer par une exception
            System.out.println("The department named '" + departmentName + "'already exists.");
        }
    }

}