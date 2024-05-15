package javaProject.Model;

public class Employee {

    private int id;
    private int start_hour;
    private int end_hour;
    private int extra_hour;
    public int departementId;


    // Constructeur
    public Employee(int id, int start_hour, int end_hour, int extra_hour,int departementId) {
        id = id;
        start_hour = start_hour;
        end_hour = end_hour;
        extra_hour = extra_hour;
        departementId=0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStart_hour() {
        return start_hour;
    }

    public void setStart_hour(int start_hour) {
        this.start_hour = start_hour;
    }

    public int getEnd_hour() {
        return end_hour;
    }

    public void setEnd_hour(int end_hour) {
        this.end_hour = end_hour;
    }

    public int getExtra_hour() {
        return extra_hour;
    }

    public void setExtra_hour(int extra_hour) {
        this.extra_hour = extra_hour;
    }

}
