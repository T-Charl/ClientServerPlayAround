package co.za.javaPlayground.Commands;

import java.io.PrintStream;
import java.util.concurrent.CopyOnWriteArrayList;

public class LaunchCommand {
    private final PrintStream out;
    private final CopyOnWriteArrayList<String> activeRobots;
    private final CopyOnWriteArrayList<PrintStream> clients;
    private final String name;
    private final String type;

    public LaunchCommand(PrintStream out, CopyOnWriteArrayList<String> activeRobots, CopyOnWriteArrayList<PrintStream> clients, String name, String type) {
        this.out = out;
        this.activeRobots = activeRobots;
        this.clients = clients;
        this.name = name;
        this.type = type;
    }

    public void execute() {
        if (!activeRobots.contains(name)) {
            activeRobots.add(name);
//            out.println("Robot " + name + " of type " + type + " launched.");
            out.println("Robot " + name + " launched successfully.");
            broadcastMessage("Robot " + name + " of type " + type + " has joined the server.");
        } else {
            out.println("Robot " + name + " already exists.");
        }
    }

    private void broadcastMessage(String message) {
        for (PrintStream client : clients) {
            client.println(message);
        }
    }
}
