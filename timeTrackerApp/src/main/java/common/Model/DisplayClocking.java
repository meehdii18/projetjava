package common.Model;

import java.time.LocalDate;
import java.time.LocalTime;

public record DisplayClocking(String publicId, String firstName, String lastName, LocalDate date, LocalTime time, String type) {

    public String getPublicId() {
        return publicId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

}
