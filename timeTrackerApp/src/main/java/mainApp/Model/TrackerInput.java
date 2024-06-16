package mainApp.Model;

import java.io.IOException;
import java.net.ServerSocket;

public class TrackerInput {

    ServerSocket server;

    public TrackerInput(int port) {
        try {
            this.server = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println(e + " : Unable to create the server socket.");
        }
    }

    public ServerSocket getServer() {
        return server;
    }

}
