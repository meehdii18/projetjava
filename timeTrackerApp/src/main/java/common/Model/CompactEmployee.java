package common.Model;

import java.io.Serializable;

public record CompactEmployee(String employeeId, String firstName, String lastName, boolean toAdd) implements Serializable {
}

// TODO add publicId form : prenom.nom + id si besoin