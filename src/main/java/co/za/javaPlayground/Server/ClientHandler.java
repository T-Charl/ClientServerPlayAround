package co.za.javaPlayground.Server;

import co.za.javaPlayground.Commands.*;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;


public class ClientHandler implements Runnable {
    private final BufferedReader in;
    private final PrintStream out;
    private final CopyOnWriteArrayList<String> activeRobots;
    private final CopyOnWriteArrayList<PrintStream> clients;
    private final String clientMachine;

    public ClientHandler(Socket socket, CopyOnWriteArrayList<String> activeRobots, CopyOnWriteArrayList<PrintStream> clients) throws IOException {
        this.clientMachine = socket.getInetAddress().getHostName();
        this.activeRobots = activeRobots;
        this.clients = clients;
        this.out = new PrintStream(socket.getOutputStream());
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        clients.add(out);
        System.out.println("Connection from " + clientMachine);
    }

    @Override
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
                    clients.remove(out);
                    break;
                } else {
                    out.println("Invalid quit command. Usage: quit <name>");
                }
                break;
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

    private void broadcast(String message) {
        for (PrintStream client : clients) {
            client.println(message);
        }
    }
}
