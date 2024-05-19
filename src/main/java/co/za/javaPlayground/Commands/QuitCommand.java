package co.za.javaPlayground.Commands;

import java.io.PrintStream;
import java.util.concurrent.CopyOnWriteArrayList;

public class QuitCommand {
    private final PrintStream out;
    private final CopyOnWriteArrayList<String> activeRobots;
    private final CopyOnWriteArrayList<PrintStream> clients;
    private final String name;

    public QuitCommand(PrintStream out, CopyOnWriteArrayList<String> activeRobots, CopyOnWriteArrayList<PrintStream> clients, String name) {
        this.out = out;
        this.activeRobots = activeRobots;
        this.clients = clients;
        this.name = name;
    }

    public void execute() {
        if (activeRobots.remove(name)) {
            out.println("Robot " + name + " has left the server.");
            broadcastMessage("Robot " + name + " has left the server.");
        } else {
            out.println("Robot " + name + " does not exist.");
        }
    }

    private void broadcastMessage(String message) {
        for (PrintStream client : clients) {
            client.println(message);
        }
    }
}
