package javaProject.Model;

public class Department {
    private int departmentId;
    private char departmentName;
    public Company company;

    public Departement(char name, int id){
        departmentId = 0;
        departmentName = name;
    }

    public int getDepartmentId(){
        return departmentId;
    }

    public void setDepartmentId(int newId){
        this.departmentId = newId;
    }

    public char getDepartmentName(){
        return departmentName;
    }

    public void setDepartmentName(char newName){
        this.departmentName  = newName;
    }
}