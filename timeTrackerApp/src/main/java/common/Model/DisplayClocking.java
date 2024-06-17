package common.Model;

import java.time.LocalDate;
import java.time.LocalTime;


/**
 * Represents a clocking record for display purposes.
 */
public record DisplayClocking(String id, String firstName, String lastName, LocalDate date, LocalTime time,
                              String type) {

    /**
     * Get the ID of the clocking record.
     * Needed for the TableView in javafx.
     *
     * @return The ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Get the first name of the individual associated with the clocking record.
     * Needed for the TableView in javafx.
     *
     * @return The first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Get the last name of the individual associated with the clocking record.
     * Needed for the TableView in javafx.
     *
     * @return The last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Get the date of the clocking record.
     * Needed for the TableView in javafx.
     *
     * @return The date.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Get the time of the clocking record.
     * Needed for the TableView in javafx.
     *
     * @return The time.
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * Get the type of the clocking record.
     * Needed for the TableView in javafx.
     *
     * @return The type.
     */
    public String getType() {
        return type;
    }

}
