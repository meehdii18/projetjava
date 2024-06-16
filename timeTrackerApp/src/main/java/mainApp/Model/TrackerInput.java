package mainApp.Model;

import java.io.IOException;
import java.net.ServerSocket;

public class TrackerInput {

    ServerSocket server;

    public TrackerInput() {
        try {
            this.server = new ServerSocket(0);
        } catch (IOException e) {
            System.out.println(e + " : Unable to create the server socket.");
        }
    }

    public int getPort() {
        return server.getLocalPort();
    }

    public ServerSocket getServer() {
        return server;
    }

}
