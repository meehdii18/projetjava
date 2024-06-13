package mainApp.Controller;

import mainApp.Model.Company;
import mainApp.Model.Department;
import mainApp.Model.Employee;

import java.time.LocalTime;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;

public class ManageCompany {

    private final Company company;

    private final MainAppController view;

    public ManageCompany(Company company, MainAppController view) {
        this.company = company;
        this.view = view;
    }

    public Company getCompany() {
        return company;
    }

    public String getCompanyName() {
        return company.getCompanyName();
    }

    public Hashtable<String, Department> getDepartments() {
        return company.getDepartmentsList();
    }

    public String addEmployee(String firstName, String lastName, String departmentName, int salary, LocalTime start_hour, LocalTime end_hour) {
        return company.addEmployee(firstName, lastName, departmentName, salary, start_hour, end_hour);
    }

    public void deleteEmploye(String employeID) {
        company.deleteEmployee(employeID);
    }

    public void saveData() {
        company.serializeCompany();
    }

    public Employee getEmployee(String employeId) {
        return company.getEmployee(employeId);
    }
}
