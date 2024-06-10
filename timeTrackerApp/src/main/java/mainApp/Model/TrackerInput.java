package mainApp.Model;

import common.Model.Clocking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TrackerInput implements Runnable {

    ServerSocket server;

    public TrackerInput(int port) {
        try {
            this.server = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println(e);
        }
    }


    public void run() {
        while (true) {
            System.out.println("listening");
            try (Socket sock = server.accept()){

                ObjectInputStream in = new ObjectInputStream(sock.getInputStream());

                try {
                    Object obj = in.readObject();
                    Clocking clocking = (Clocking) obj;

                    if(obj != null) {
                        System.out.println(clocking);
                    }

                } catch (ClassNotFoundException ex) {
                    System.out.println(ex);
                }

            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

}
