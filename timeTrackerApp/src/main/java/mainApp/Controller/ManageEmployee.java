package timeTrackerApp.Controller;
import timeTrackerApp.Model.Employee;

package javaProject.Controller;


public class ManageEmployee{

    private ArrayList<Employee> employees;


    public ManageEmployee() {
        employees = new ArrayList<>();
    }

    //CRUD
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void removeEmployee(int id) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId() == id) {
                employees.remove(i);
            }
        }
    }


    public Employee findEmployeeById(int id) {
        for (Employee employee : employees) { //for each
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }

    public void updateEmployee(int id, int start_hour, int end_hour, int extra_hour) {
        Employee employee = findEmployeeById(id);
        if (employee != null) {
            employee.setStart_hour(start_hour);
            employee.setEnd_hour(end_hour);
            employee.setExtra_hour(extra_hour);
        }
    }

    public ArrayList<Employee> getAllEmployees() {
        return employees;
    }


    public Employee getEmployee(int id){
        return employees.get(id);
    }
}