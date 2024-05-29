package mainApp.Model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataStorage implements Serializable {
    private List<Employee> employees;
    private List<Department> departments;
    private List<Clocking> clockingList;

    public DataStorage() {
        employees = new ArrayList<>();
        departments = new ArrayList<>();
    }
    // Méthodes pour gérer les employés

    public Employee getEmployee(int id) {
        for (Employee emp : employees) {
            if (emp.getId() == id) {
                return emp;
            }
        }
        return null;
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }




    public Department getDepartment(int id) {
        for (Department dep : departments) {
            if (dep.getDepartmentId() == id) {
                return dep;
            }
        }
        return null;
    }

    public List<Department> getAllDepartments() {
        return new ArrayList<>(departments);
    }

}
/*reste à stocker les autres données*/