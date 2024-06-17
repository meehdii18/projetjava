package common.Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;


/**
 * Represents a clocking record for an employee.
 * Contains the employee ID, date, and clocking hour.
 *
 * @param idEmployee
 * @param date
 * @param hour
 */
public record Clocking(String idEmployee, LocalDate date, LocalTime hour) implements Serializable {

}
