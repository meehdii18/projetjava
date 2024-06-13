package mainApp.Controller;
import mainApp.Model.Employee;

import java.time.LocalTime;
import java.util.ArrayList;

// TODO : delete contenu et refaire un vrai code

public class ManageEmployee{

    private ArrayList<Employee> employees;


    public ManageEmployee() {
        employees = new ArrayList<>();
    }

    //CRUD
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void removeEmployee(String id) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId() == id) {
                employees.remove(i);
            }
        }
    }

    public Employee findEmployeeById(String id) {
        for (Employee employee : employees) { //for each
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }

    public void updateEmployee(String id, LocalTime start_hour, LocalTime end_hour, LocalTime extra_hour) {
        Employee employee = findEmployeeById(id);
        if (employee != null) {
            employee.setStartHour(start_hour);
            employee.setEndHour(end_hour);
            employee.setExtraHour(extra_hour);
        }
    }

    public ArrayList<Employee> getAllEmployees() {
        return employees;
    }

    public String findEmployeeDetails(String id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee.getDetails(); // Return the detail String from Employee
            }
        }
        return null; // Return null if no Employee is found
    }


}