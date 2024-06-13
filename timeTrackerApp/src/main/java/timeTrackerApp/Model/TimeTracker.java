package timeTrackerApp.Model;

public class TimeTracker {

    private String address;

    private int socket;

    public TimeTracker() {
        this.address = "localhost";
        this.socket = 1234;

        // TODO : change default values (by constants ?)
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSocket() {
        return socket;
    }

    public void setSocket(int socket) {
        this.socket = socket;
    }
}
