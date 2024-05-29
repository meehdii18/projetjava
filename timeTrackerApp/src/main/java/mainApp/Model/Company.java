package mainApp.Model;

import java.io.Serializable;

public class Company implements Serializable {
    private int companyId;
    private String companyName;
    public Department[] department;

    public Company(String name){
        companyId = 0;
        companyName = name;
    }

    public int getCompanyIdId(){
        return companyId;
    }
    public void setCompanyId(int newId){
        this.companyId = newId;
    }
    public String getCompanyName(){
        return companyName;
    }
    public void setCompanyName(String newName){
        this.companyName = newName;
    }

}