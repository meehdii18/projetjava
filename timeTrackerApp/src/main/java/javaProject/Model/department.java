package javaProject.Model;

public class Department {
    private int departmentId;
    private char departmentName;
    private int idCompany;

    public Departement(){

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