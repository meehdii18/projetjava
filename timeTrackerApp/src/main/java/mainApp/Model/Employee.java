package mainApp.Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class Employee implements Serializable, Cloneable {

    private final String id;

    private String firstName;
    private String lastName;
    private String departmentName;
    private float salary;

    private LocalTime startHour;
    private LocalTime endHour;

    private ClockingHistory clockingHistory;

    // Constructeur
    public Employee(String firstName, String lastName, String departmentName, float salary, LocalTime startHour, LocalTime endHour) {
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.departmentName = departmentName;
        this.salary = salary;
        this.startHour = startHour;
        this.endHour = endHour;
        this.clockingHistory = new ClockingHistory();
    }

    public String getId() {
        return id;
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

    public float getExtraHour() {
        float extraTime = 0;

        for (LocalDate date : clockingHistory.getDaysClocked()) {
            LocalTime in = clockingHistory.queryClockIn(date);
            LocalTime out = clockingHistory.queryClockOut(date);

            if (in != null) {
                extraTime += startHour.getHour()*60 + startHour.getMinute();
                extraTime += in.getHour()*60 + in.getMinute();
            }

            if (out != null) {
                extraTime -= (endHour.getHour()*60 + endHour.getMinute());
                extraTime -= (out.getHour()*60 + out.getMinute());
            }
        }

        return extraTime / 60;
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

    public void addClocking(LocalDate date, LocalTime time) {
        if (clockingHistory.queryClockIn(date) == null) {
            clockingHistory.addClockIn(date, time);
        } else if (clockingHistory.queryClockOut(date) == null) {
            clockingHistory.addClockOut(date, time);
        }
    }

    public Employee clone(){
        try {

            return (Employee) super.clone();

        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
