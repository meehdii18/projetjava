package timeTrackerApp.Controller;

import common.Model.Clocking;
import timeTrackerApp.Model.TimeTracker;
import timeTrackerApp.View.MainScreenController;

import java.time.LocalDate;
import java.time.LocalTime;

public class TimeTrackerController {

    private final TimeTracker model;

    private final MainScreenController mainView;

    public TimeTrackerController(TimeTracker model, MainScreenController mainView) {
        this.model = model;
        this.mainView = mainView;
    }

    public TimeTracker getModel() {
        return model;
    }

    public void newClocking(String employeeId, LocalDate now, LocalTime approxTime) {

        Clocking clocking = new Clocking(employeeId, now, approxTime);

        model.sendClocking(clocking); // TODO : surement déplacer la méthode dans le controller
    }
}
