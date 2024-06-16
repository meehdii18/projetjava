package mainApp.Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Hashtable;
import java.util.Set;

public class ClockingHistory implements Serializable {

    private final Hashtable<LocalDate, LocalTime> clockIn = new Hashtable<>();

    private final Hashtable<LocalDate, LocalTime> clockOut = new Hashtable<>();

    // Constructeur par défaut
    public ClockingHistory() {
    }

    public void addClockIn(LocalDate date, LocalTime hour) {
        clockIn.put(date, hour);
    }

    public void addClockOut(LocalDate date, LocalTime hour) {
        clockOut.put(date, hour);
    }

    public LocalTime queryClockIn(LocalDate date) {
        return clockIn.get(date);
    }

    public LocalTime queryClockOut(LocalDate date) {
        return clockOut.get(date);
    }

    public Set<LocalDate> getDaysClocked() {
        return clockIn.keySet();
    }
}