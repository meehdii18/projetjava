package mainApp.Model;

import java.util.Hashtable;

public class Department {

    private String departmentName;

    private final Hashtable<String, Employee> employeesList = new Hashtable<>();

    public Department(String name){
        departmentName = name;
    }

    public String getDepartmentName(){
        return departmentName;
    }

    public void setDepartmentName(String newName){
        this.departmentName  = newName;
    }

    public Employee getEmployee(String idEmploye) {
        return employeesList.get(idEmploye);
    }

    public void addEmployee(Employee employee) {
        this.employeesList.put(employee.getId(), employee);
    }
}