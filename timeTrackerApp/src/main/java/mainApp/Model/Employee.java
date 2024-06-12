package mainApp.Model;

import common.Model.Clocking;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Hashtable;
import java.util.Set;

public class Employee implements Serializable {

    private String id;

    private String firstName;
    private String lastName;
    private String departmentName;
    private float salary;

    private LocalTime startHour;
    private LocalTime endHour;
    private LocalTime extraHour;

    private ClockingHistory clockingHistory;

    private Set<Clocking> irregularities;

    // Constructeur
    public Employee(String id, String firstName, String lastName, String departmentName, float salary, LocalTime startHour, LocalTime endHour, LocalTime extraHour, ClockingHistory clockingHistory, Set<Clocking> irregularities) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.departmentName = departmentName;
        this.salary = salary;
        this.startHour = startHour;
        this.endHour = endHour;
        this.extraHour = extraHour;
        this.clockingHistory = clockingHistory;
        this.irregularities = irregularities;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalTime getStartHour() {
        return startHour;
    }

    public void setStartHour(LocalTime startHour) {
        this.startHour = startHour;
    }

    public LocalTime getEndHour() {
        return endHour;
    }

    public void setEndHour(LocalTime endHour) {
        this.endHour = endHour;
    }

    public LocalTime getExtraHour() {
        return extraHour;
    }

    public void setExtraHour(LocalTime extraHour) {
        this.extraHour = extraHour;
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
        return "ID: " + id + "\nFirst Name: " + firstName + "Last Name" + lastName + "\nDepartment: " + departmentName + "\nSalary: $" + salary;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ClockingHistory getClockingHistory() {
        return clockingHistory;
    }

    public void setClockingHistory(ClockingHistory clockingHistory) {
        this.clockingHistory = clockingHistory;
    }

    public Set<Clocking> getIrregularities() {
        return irregularities;
    }

    public void setIrregularities(Set<Clocking> irregularities) {
        this.irregularities = irregularities;
    }

    public void addClocking(LocalDate date, LocalTime time) {
        if (clockingHistory.queryClockIn(date) == null) {
            clockingHistory.addClockIn(date, time);
        } else if (clockingHistory.queryClockOut(date) == null) {
            clockingHistory.addClockOut(date, time);
        } else {
            irregularities.add(new Clocking(id, date, time));
        }
    }
}
