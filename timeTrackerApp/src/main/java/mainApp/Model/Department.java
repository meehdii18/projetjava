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

    public void addEmploye(Employee employee) {
        employeesList.put(employee.getId(), employee); // TODO : check si employé pas existant
    }

    public void deleteEmploye(String employeId) {
        employeesList.remove(employeId); // TODO : check si employé existant
    }

    public Employee getEmployee(String idEmploye) {
        return employeesList.get(idEmploye);
    }

    public Hashtable<String, Employee> getEmployeesList() {
        return employeesList;
    }
}