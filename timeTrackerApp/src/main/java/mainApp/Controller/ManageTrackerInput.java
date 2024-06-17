package mainApp.Controller;

import common.Model.Clocking;
import common.Model.CompactEmployee;
import mainApp.Model.Company;
import mainApp.Model.TrackerInput;

import java.io.*;
import java.net.Socket;

import static common.Model.Constants.FETCH;
import static common.Model.Constants.QUIT;

/**
 * This class is responsible for managing the input from the tracker.
 */
public class ManageTrackerInput implements Runnable {

    private final TrackerInput input;
    private final Company company;

    /**
     * Constructor for the ManageTrackerInput class.
     *
     * @param company The company object.
     */
    public ManageTrackerInput(Company company) {
        this.input = new TrackerInput();
        this.company = company;

        company.setPort(input.getPort());
    }

    /**
     * The run method for the Runnable interface.
     * It listens for incoming connections and handles the received data.
     */
    public void run() {
        boolean running = true;
        while (running) {
            System.out.println("listening");
            try (Socket sock = input.getServer().accept()) {

                ObjectInputStream in = new ObjectInputStream(sock.getInputStream());

                boolean streamNotEmpty = true;

                while (streamNotEmpty) {

                    try {
                        Object obj = in.readObject();

                        if (obj instanceof Clocking clocking) {

                            try {
                                company.addClockingToEmployee(clocking.idEmployee(), clocking.date(), clocking.hour());
                            } catch (RuntimeException runtimeException) {
                                System.out.println("Impossible d'ajouter le pointage");
                            }

                            company.serializeCompany();

                        } else if (obj instanceof String instruction) {

                            if (instruction.equals(FETCH)) {
                                ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());

                                for (CompactEmployee employee : company.sendLocalEmployeeToDistant()) {

                                    out.writeObject(employee);

                                    out.flush();

                                }

                                out.close();

                            } else if (instruction.equals(QUIT)) {
                                throw new RuntimeException();
                            } else {
                                System.out.println("Instruction not recognized : " + instruction);
                            }
                        }
                    } catch (EOFException eofException) {
                        streamNotEmpty = false;
                    } catch (ClassNotFoundException ex) {
                        System.out.println(ex + " : Class not found");
                    }
                }
            } catch (IOException e) {
                System.out.println(e + " : IO exception");
            } catch (RuntimeException runtimeException) {
                running = false;
            }
        }
    }
}