package javaProject.Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public record Clocking(int idEmploye, LocalDate date, LocalTime hour) implements Serializable {

}
