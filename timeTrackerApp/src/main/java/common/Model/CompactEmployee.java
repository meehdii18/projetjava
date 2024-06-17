package common.Model;

import java.io.Serializable;

/**
 * Represents a compact employee with basic information.
 *
 * @param employeeId
 * @param firstName
 * @param lastName
 */
public record CompactEmployee(String employeeId, String firstName, String lastName) implements Serializable {
}
