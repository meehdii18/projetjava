package mainApp.Model;

import java.io.Serializable;
import java.time.LocalTime;

public class Employee implements Serializable {

    private String id;

    private String name;
    private String departmentName;
    private float salary;

    private LocalTime start_hour;
    private LocalTime end_hour;
    private LocalTime extra_hour;

    // Constructeur
    public Employee(String id, String name, String departmentName, float salary, LocalTime start_hour, LocalTime end_hour, LocalTime extra_hour) {
        this.id = id;
        this.name = name;
        this.departmentName = departmentName;
        this.salary = salary;
        this.start_hour = start_hour;
        this.end_hour = end_hour;
        this.extra_hour = extra_hour;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalTime getStart_hour() {
        return start_hour;
    }

    public void setStart_hour(LocalTime start_hour) {
        this.start_hour = start_hour;
    }

    public LocalTime getEnd_hour() {
        return end_hour;
    }

    public void setEnd_hour(LocalTime end_hour) {
        this.end_hour = end_hour;
    }

    public LocalTime getExtra_hour() {
        return extra_hour;
    }

    public void setExtra_hour(LocalTime extra_hour) {
        this.extra_hour = extra_hour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public String getDetails() {
        return "ID: " + id + "\nName: " + name + "\nDepartment: " + departmentName + "\nSalary: $" + salary;
    }
}
