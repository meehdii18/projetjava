package mainApp.Model;

import java.io.Serializable;
import java.util.Hashtable;

public class Department implements Serializable {

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

    public void addEmployee(Employee employee) {
        employeesList.put(employee.getId(), employee); // TODO : check si employé pas existant
    }

    public void deleteEmployee(String employeeId) {
        employeesList.remove(employeeId); // TODO : check si employé existant
    }

    public Employee getEmployee(String idEmployee) {
        return employeesList.get(idEmployee);
    }

    public Hashtable<String, Employee> getEmployeesList() {
        return employeesList;
    }
}