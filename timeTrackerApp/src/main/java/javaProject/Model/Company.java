package javaProject.Model;

public class Company {
    private int companyId;
    private char companyName;
    public Department[] department;

    public Company(char name){
        companyId = 0;
        companyName = name;
    }

    public int getCompanyIdId(){
        return companyId;
    }
    public void setCompanyId(int newId){
        this.companyId = id;
    }
    public char getCompanyName(){
        return companyName;
    }
    public void setCompanyName(int newName){
        this.companyName = name;
    }

}