package mainApp.Model;

import java.io.Serializable;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Hashtable;
import java.util.Set;

/**
 * This class represents the clocking history of an employee.
 * It manages the clock-in and clock-out times for each day.
 * The clock-in and clock-out times are stored in two separate Hashtables,
 * with the date as the key and the time as the value.
 */
public class ClockingHistory implements Serializable {

    /**
     * Hashtable to store the clock-in times.
     * The key is the date and the value is the clock-in time.
     */
    private final Hashtable<LocalDate, LocalTime> clockIn = new Hashtable<>();

    /**
     * Hashtable to store the clock-out times.
     * The key is the date and the value is the clock-out time.
     */
    private final Hashtable<LocalDate, LocalTime> clockOut = new Hashtable<>();

    /**
     * Default constructor for ClockingHistory.
     */
    public ClockingHistory() {
    }

    /**
     * Adds a clock-in time for a specific date.
     *
     * @param date The date of the clock-in.
     * @param hour The clock-in time.
     */
    public void addClockIn(LocalDate date, LocalTime hour) {
        clockIn.put(date, hour);
    }

    /**
     * Adds a clock-out time for a specific date.
     *
     * @param date The date of the clock-out.
     * @param hour The clock-out time.
     */
    public void addClockOut(LocalDate date, LocalTime hour) {
        clockOut.put(date, hour);
    }

    /**
     * Queries the clock-in time for a specific date.
     *
     * @param date The date to query.
     * @return The clock-in time for the specified date.
     */
    public LocalTime queryClockIn(LocalDate date) {
        return clockIn.get(date);
    }

    /**
     * Queries the clock-out time for a specific date.
     *
     * @param date The date to query.
     * @return The clock-out time for the specified date.
     */
    public LocalTime queryClockOut(LocalDate date) {
        return clockOut.get(date);
    }

    /**
     * Gets the set of dates for which clock-in times are available.
     *
     * @return A set of dates for which clock-in times are available.
     */
    public Set<LocalDate> getDaysClocked() {
        return clockIn.keySet();
    }
}