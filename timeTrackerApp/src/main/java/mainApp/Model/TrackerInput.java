package mainApp.Model;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * This class represents a server socket for the tracker input.
 * It manages the creation and retrieval of the server socket and its port.
 */
public class TrackerInput {

    /**
     * The server socket for the tracker input.
     */
    private ServerSocket server;

    /**
     * Default constructor for TrackerInput.
     * It tries to create a new server socket and assigns it to the server variable.
     * If the creation fails, it prints an error message.
     */
    public TrackerInput() {
        try {
            this.server = new ServerSocket(0);
        } catch (IOException e) {
            System.out.println(e + " : Unable to create the server socket.");
        }
    }

    /**
     * Retrieves the port of the server socket.
     *
     * @return The port of the server socket.
     */
    public int getPort() {
        return server.getLocalPort();
    }

    /**
     * Retrieves the server socket.
     *
     * @return The server socket.
     */
    public ServerSocket getServer() {
        return server;
    }
}