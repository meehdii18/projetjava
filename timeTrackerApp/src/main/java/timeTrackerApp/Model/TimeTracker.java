package timeTrackerApp.Model;

public class TimeTracker {

    private String address;

    private String socket;

    public TimeTracker() {
        this.address = "IP address";
        this.socket = "socket";
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }
}
