package mainApp.Controller;

import common.Model.DisplayClocking;
import mainApp.Model.Company;
import mainApp.Model.Department;
import mainApp.Model.Employee;
import mainApp.View.MainAppController;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalTime;
import java.util.HashSet;
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

    public int getSocket() {
        return company.getPort();
    }

    public Hashtable<String, Department> getDepartments() {
        return company.getDepartmentsList();
    }

    public String addEmployee(String firstName, String lastName, String departmentName, int salary, LocalTime start_hour, LocalTime end_hour) {
        return company.addEmployee(firstName, lastName, departmentName, salary, start_hour, end_hour);
    }

    public void deleteEmployee(String employeeID) {
        company.deleteEmployee(employeeID);
    }

    public void updateData(Employee employee) {

        company.updateEmployee(employee);

        saveData();
    }

    public void saveData() {
        company.serializeCompany();
    }

    public Employee getEmployee(String employeeId) {
        return company.getCopyOfEmployee(employeeId);
    }

    public HashSet<DisplayClocking> getClocking() {
        return company.getAllClocking();
    }

    public HashSet<DisplayClocking> getClocking(String employeeId) {
        return company.getClockingOfEmployee(employeeId);
    }

    public void stopServerThread() {
        try (Socket sock = new Socket("localhost", getSocket())) { // TODO : change here

            ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());

            out.writeObject("quit"); // TODO : change message by constant

            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


