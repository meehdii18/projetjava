package mainApp.Model;

import mainApp.Model.Employee;
import java.io.*;
import java.time.LocalTime;

public class StorageSerializer {

    // Méthode pour sérialiser un objet Employee
    public static void serializeEmployee(Employee employee, String fileName) {
        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(employee);
            System.out.println("L'objet Employee a été sérialisé et écrit dans " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour désérialiser un objet Employee
    public static Employee deserializeEmployee(String fileName) {
        Employee employee = null;
        try (FileInputStream fileIn = new FileInputStream(fileName);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            employee = (Employee) in.readObject();
            System.out.println("L'objet Employee a été désérialisé depuis " + fileName);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return employee;
    }


    public static void main(String[] args) {
        Employee emp = new Employee("1", "Sara", "IT", 50000.0f, LocalTime.of(9, 0), LocalTime.of(17, 0), LocalTime.of(18, 0));
        String fileName = "employee.ser";

        // Sérialiser l'objet
        serializeEmployee(emp, fileName);

        // Désérialiser l'objet
        Employee deserializedEmp = deserializeEmployee(fileName);
        if (deserializedEmp != null) {
            System.out.println("Détails de l'employé désérialisé :");
            System.out.println(deserializedEmp.getDetails());
        }
    }


}
