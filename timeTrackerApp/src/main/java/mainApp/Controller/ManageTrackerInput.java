package mainApp.Controller;

import common.Model.Clocking;
import mainApp.Model.Company;
import mainApp.Model.TrackerInput;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ManageTrackerInput implements Runnable {

    private TrackerInput input;

    private Company company;


    public ManageTrackerInput(int sock, Company company) {
        this.input = new TrackerInput(sock);
        this.company = company;
    }

    public TrackerInput getInput() {
        return input;
    }

    public void setInput(TrackerInput input) {
        this.input = input;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void run() {
        while (true) {
            System.out.println("listening");
            try (Socket sock = input.getServer().accept()) {

                ObjectInputStream in = new ObjectInputStream(sock.getInputStream());

                try {
                    Object obj = in.readObject();

                    System.out.println(obj.getClass());

                    Clocking clocking = (Clocking) obj;

                    if (obj != null) {
                        System.out.println(clocking);

                        company.addClockingToEmployee(clocking.idEmploye(),clocking.date(),clocking.hour());

                        company.serializeCompany();
                    }
                    // TODO : gérer les exceptions
                } catch (ClassNotFoundException ex) {
                    System.out.println(ex);
                }

            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
