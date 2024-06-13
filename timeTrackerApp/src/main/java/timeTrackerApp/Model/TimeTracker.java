package timeTrackerApp.Model;

import common.Model.Clocking;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

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

    public void sendClocking(Clocking clocking) {

        try (Socket sock = new Socket(address, socket)) {

            ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());

            out.writeObject(clocking);

            out.flush();

        } catch (IOException e) {
            System.out.println(e + "ici");
        }

        // TODO : Utiliser clocking en le stockant localement (temporairement) puis en l'envoyant via réseau à la mainApp

    }
}
