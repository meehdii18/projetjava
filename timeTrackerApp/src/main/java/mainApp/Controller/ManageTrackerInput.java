package mainApp.Controller;

import common.Model.Clocking;
import common.Model.CompactEmployee;
import mainApp.Model.Company;
import mainApp.Model.TrackerInput;

import java.io.*;
import java.net.Socket;

public class ManageTrackerInput implements Runnable {

    private final TrackerInput input;

    private final Company company;

    public ManageTrackerInput(Company company) {
        this.input = new TrackerInput();
        this.company = company;

        company.setPort(input.getPort());
    }

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

                        System.out.println(obj.getClass());

                        if (obj instanceof Clocking clocking) {

                            System.out.println(clocking);

                            try {
                                company.addClockingToEmployee(clocking.idEmployee(), clocking.date(), clocking.hour());
                            } catch (RuntimeException runtimeException) {
                                System.out.println("Impossible d'ajouter le pointage");
                            }

                            company.serializeCompany();

                        } else if (obj instanceof String instruction) {

                            if (instruction.equals("fetch")) {
                                ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());

                                for (CompactEmployee employee : company.sendLocalEmployeeToDistant()) {

                                    out.writeObject(employee);

                                    out.flush();

                                }

                                out.close();

                            } else if (instruction.equals("quit")) {
                                throw new RuntimeException();
                            } else {
                                System.out.println("Instruction not recognized : " + instruction);
                            }
                        }
                    } catch (EOFException eofException) {
                        streamNotEmpty = false;
                    } catch (ClassNotFoundException ex) {
                        System.out.println(ex + " Class not found");
                    }
                }
            } catch (IOException e) {
                System.out.println(e + " io exception");
            } catch (RuntimeException runtimeException) {
                running = false;// TODO : gérer les exceptions
            }
        }
    }
}
