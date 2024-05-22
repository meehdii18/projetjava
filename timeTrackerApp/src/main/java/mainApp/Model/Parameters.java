package mainApp.Model;

import java.io.Serializable;

public class Parameters implements Serializable {

    private String trackerIpAdresse;
    private int trackerSocket;


    public Parameters(String trackerIpAdresse, int trackerSocket) {
            this.trackerIpAdresse = trackerIpAdresse;
            this.trackerSocket=trackerSocket;
    }

    public String getTrackerIpAdresse() {
        return trackerIpAdresse;
    }
    public void setTrackerIpAdresse(String trackerIpAdresse) {
        this.trackerIpAdresse = trackerIpAdresse;
    }
    public int getTrackerSocket() {
        return trackerSocket;
    }

    public void setTrackerIpAdresse(int trackerSocket) {
        this.trackerSocket = trackerSocket;
    }

}
