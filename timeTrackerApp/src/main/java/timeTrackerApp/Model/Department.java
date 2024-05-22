package timeTrackerApp.Model;

public class Department {
    private int departmentId;
    private String departmentName;
    public Company company;

    public Department(String name, int id){
        departmentId = 0;
        departmentName = name;
    }

    public int getDepartmentId(){
        return departmentId;
    }

    public void setDepartmentId(int newId){
        this.departmentId = newId;
    }

    public String getDepartmentName(){
        return departmentName;
    }

    public void setDepartmentName(String newName){
        this.departmentName  = newName;
    }
}