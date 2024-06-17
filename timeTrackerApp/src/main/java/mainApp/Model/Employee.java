package mainApp.Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

/**
 * This class represents an employee in the system.
 * It manages the employee's personal details, working hours, and clocking history.
 * The clocking history is managed by an instance of the ClockingHistory class.
 */
public class Employee implements Serializable, Cloneable {

    /**
     * The unique identifier for the employee.
     */
    private final String id;

    /**
     * The first name of the employee.
     */
    private String firstName;

    /**
     * The last name of the employee.
     */
    private String lastName;

    /**
     * The name of the department the employee belongs to.
     */
    private String departmentName;

    /**
     * The salary of the employee.
     */
    private float salary;

    /**
     * The start hour of the employee's working day.
     */
    private LocalTime startHour;

    /**
     * The end hour of the employee's working day.
     */
    private LocalTime endHour;

    /**
     * The clocking history of the employee.
     */
    private ClockingHistory clockingHistory;

    /**
     * Constructs an Employee with the given parameters.
     * It also initializes the clockingHistory with a new ClockingHistory object.
     *
     * @param firstName      The first name of the employee.
     * @param lastName       The last name of the employee.
     * @param departmentName The name of the department the employee belongs to.
     * @param salary         The salary of the employee.
     * @param startHour      The start hour of the employee's working day.
     * @param endHour        The end hour of the employee's working day.
     */
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

    /**
     * Retrieves the unique identifier of the employee.
     *
     * @return The unique identifier of the employee.
     */
    public String getId() {
        return id;
    }

    /**
     * Retrieves the start hour of the employee's working day.
     *
     * @return The start hour of the employee's working day.
     */
    public LocalTime getStartHour() {
        return startHour;
    }

    /**
     * Sets the start hour of the employee's working day.
     *
     * @param startHour The start hour to set for the employee's working day.
     */
    public void setStartHour(LocalTime startHour) {
        this.startHour = startHour;
    }

    /**
     * Retrieves the end hour of the employee's working day.
     *
     * @return The end hour of the employee's working day.
     */
    public LocalTime getEndHour() {
        return endHour;
    }

    /**
     * Sets the end hour of the employee's working day.
     *
     * @param endHour The end hour to set for the employee's working day.
     */
    public void setEndHour(LocalTime endHour) {
        this.endHour = endHour;
    }

    /**
     * Calculates and retrieves the extra hours worked by the employee.
     * The calculation is based on the clock-in and clock-out times for each day.
     * If the employee has clocked in and out for the same date, the extra time is added to the total.
     * The total extra time is then divided by 60 to convert it to hours.
     *
     * @return The total extra hours worked by the employee.
     */
    public float getExtraHour() {
        float extraTime = 0;

        for (LocalDate date : clockingHistory.getDaysClocked()) {
            LocalTime in = clockingHistory.queryClockIn(date);
            LocalTime out = clockingHistory.queryClockOut(date);

            if (in != null && out != null) {
                extraTime += startHour.getHour() * 60 + startHour.getMinute();
                extraTime += in.getHour() * 60 + in.getMinute();
                extraTime -= (endHour.getHour() * 60 + endHour.getMinute());
                extraTime -= (out.getHour() * 60 + out.getMinute());
            }
        }

        return extraTime / 60;
    }

    /**
     * Retrieves the name of the department the employee belongs to.
     *
     * @return The name of the department the employee belongs to.
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * Sets the name of the department the employee belongs to.
     *
     * @param departmentName The name of the department to set for the employee.
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * Retrieves the salary of the employee.
     *
     * @return The salary of the employee.
     */
    public float getSalary() {
        return salary;
    }

    /**
     * Sets the salary of the employee.
     *
     * @param salary The salary to set for the employee.
     */
    public void setSalary(float salary) {
        this.salary = salary;
    }

    /**
     * Intended for debug purposes only.
     * Retrieves the details of the employee.
     * The details include the ID, first name, last name, department name, and salary of the employee.
     *
     * @return A string representation of the employee's details.
     */
    public String getDetails() {
        return "ID: " + id + "\nFirst Name: " + firstName + "Last Name" + lastName + "\nDepartment: " + departmentName + "\nSalary: $" + salary;
    }

    /**
     * Retrieves the first name of the employee.
     *
     * @return The first name of the employee.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the employee.
     *
     * @param firstName The first name to set for the employee.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retrieves the last name of the employee.
     *
     * @return The last name of the employee.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the employee.
     *
     * @param lastName The last name to set for the employee.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retrieves the clocking history of the employee.
     *
     * @return The clocking history of the employee.
     */
    public ClockingHistory getClockingHistory() {
        return clockingHistory;
    }

    /**
     * Sets the clocking history of the employee.
     *
     * @param clockingHistory The clocking history to set for the employee.
     */
    public void setClockingHistory(ClockingHistory clockingHistory) {
        this.clockingHistory = clockingHistory;
    }

    /**
     * Adds a clocking event for the employee.
     * If the employee has not clocked in on the given date, a clock-in event is added.
     * If the employee has clocked in but not clocked out on the given date, a clock-out event is added.
     *
     * @param date The date of the clocking event.
     * @param time The time of the clocking event.
     */
    public void addClocking(LocalDate date, LocalTime time) {
        if (clockingHistory.queryClockIn(date) == null) {
            clockingHistory.addClockIn(date, time);
        } else if (clockingHistory.queryClockOut(date) == null) {
            clockingHistory.addClockOut(date, time);
        }
    }

    /**
     * Creates and returns a copy of this Employee object.
     * The precise meaning of "copy" may depend on the class of the object.
     * The general intent is that, for any object x, the expression:
     * x.clone() != x will be true, and that the expression:
     * x.clone().getClass() == x.getClass() will be true, but these are not absolute requirements.
     *
     * @return a clone of this instance.
     * @throws RuntimeException if the object's class does not support the Cloneable interface.
     *                                    Subclasses that override the clone method can also throw this exception to indicate that an instance cannot be cloned.
     */
    public Employee clone() {
        try {

            return (Employee) super.clone();

        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
