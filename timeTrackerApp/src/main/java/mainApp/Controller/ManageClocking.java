package mainApp.Controller;


import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;
import mainApp.Model.ClockingHistory;

public class ManageClocking {
    private final Map<Integer, Map<DayOfWeek, ClockingHistory>> employeeSchedules = new HashMap<>();

    // Ajouter un pointage pour un employé pour un jour spécifique
    public void addClocking(int employeeId, DayOfWeek day, ClockingHistory clockingHistory) {
        employeeSchedules
                .computeIfAbsent(employeeId, k -> new HashMap<>())
                .put(day, clockingHistory);
    }

    // Récupérer le pointage pour un employé pour un jour spécifique
    public ClockingHistory getClocking(int employeeId, DayOfWeek day) {
        Map<DayOfWeek, ClockingHistory> schedule = employeeSchedules.get(employeeId);
        if (schedule != null) {
            return schedule.get(day);
        }
        return null;
    }

    // Supprimer le pointage pour un employé pour un jour spécifique
    public void removeClocking(int employeeId, DayOfWeek day) {
        Map<DayOfWeek, ClockingHistory> schedule = employeeSchedules.get(employeeId);
        if (schedule != null) {
            schedule.remove(day);
        }
    }

    // Récupérer tous les pointages pour un employé
    public Map<DayOfWeek, ClockingHistory> getAllClockings(int employeeId) {
        return employeeSchedules.getOrDefault(employeeId, new HashMap<>());
    }

    // Récupérer tous les employés et leurs pointages
    public Map<Integer, Map<DayOfWeek, ClockingHistory>> getAllEmployeeSchedules() {
        return new HashMap<>(employeeSchedules);
    }
}



