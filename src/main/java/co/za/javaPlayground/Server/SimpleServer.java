package co.za.javaPlayground.Server;

import co.za.javaPlayground.Commands.*;

import java.io.*;
import java.net.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class SimpleServer implements Runnable {

    public static final int PORT = 5000;
    private static final CopyOnWriteArrayList<String> activeRobots = new CopyOnWriteArrayList<>();
    private static final CopyOnWriteArrayList<PrintStream> clients = new CopyOnWriteArrayList<>();
    private final BufferedReader in;
    private final PrintStream out;
    private final String clientMachine;

    public SimpleServer(Socket socket) throws IOException {
        clientMachine = socket.getInetAddress().getHostName();
        System.out.println("Connection from " + clientMachine);

        out = new PrintStream(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        clients.add(out);
        System.out.println("Waiting for client...");
    }

    public void run() {
        try {
            String messageFromClient;
            while ((messageFromClient = in.readLine()) != null) {
                System.out.println("Message \"" + messageFromClient + "\" from " + clientMachine);
                handleCommand(messageFromClient);
            }
        } catch (IOException ex) {
            System.out.println("Shutting down single client server");
        } finally {
            clients.remove(out);
            closeQuietly();
        }
    }

    private void handleCommand(String command) {
        String[] parts = command.split(" ");
        String mainCommand = parts[0];

        switch (mainCommand.toLowerCase()) {
            case "launch":
                if (parts.length >= 3) {
                    new LaunchCommand(out, activeRobots, clients, parts[1], parts[2]).execute();
                } else {
                    out.println("Invalid launch command. Usage: launch <name> <type>");
                }
                break;
            case "robots":
                new ListRobotsCommand(out, activeRobots).execute();
                break;
            case "quit":
                if (parts.length == 2) {
                    new QuitCommand(out, activeRobots, clients, parts[1]).execute();
                    clients.remove(out); // Ensure the client is removed from the broadcast list
                    break;
                } else {
                    out.println("Invalid quit command. Usage: quit <name>");
                }
//                break;
            default:
                out.println("Unknown command: " + mainCommand);
        }
    }

    private void closeQuietly() {
        try {
            in.close();
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
