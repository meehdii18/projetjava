package timeTrackerApp.Model;

import common.Model.Clocking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class mainAppConnection {

    public static void sendClocking(Clocking clocking, String address, int socket) {

        try (Socket sock = new Socket(address, socket)) {

            ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());

            out.writeObject(clocking);

            out.flush();


        } catch (IOException e) {
            System.out.println(e);
        }

    }
}
