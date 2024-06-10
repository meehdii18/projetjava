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
}