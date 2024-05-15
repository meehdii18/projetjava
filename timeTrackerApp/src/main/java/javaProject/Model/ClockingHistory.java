package javaProject.Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Hashtable;

public class ClockingHistory implements Serializable {

    private final Hashtable<LocalDate, LocalTime> clock_in = new Hashtable<>();

    private final Hashtable<LocalDate, LocalTime> clock_out = new Hashtable<>();

    public void addClockIn(LocalDate date, LocalTime hour) {
        clock_in.put(date, hour);
    }

    public void addClockOut(LocalDate date, LocalTime hour) {
        clock_out.put(date, hour);
    }

    public LocalTime queryClockIn(LocalDate date) {
        return clock_in.get(date);
    }

    public LocalTime queryClockOut(LocalDate date) {
        return clock_out.get(date);
    }

}
