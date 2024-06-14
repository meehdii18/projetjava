package mainApp.Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Hashtable;

public class ClockingHistory implements Serializable {

    // TODO : peut être à modifier et avoir qu'une seule hashtable clock comme le prof avait dit, pas de diff entre in et out

    private final Hashtable<LocalDate, LocalTime> clock_in = new Hashtable<>();

    private final Hashtable<LocalDate, LocalTime> clock_out = new Hashtable<>();

    // Constructeur par défaut
    public ClockingHistory() {
    }

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

    // TODO : irregularities here
}