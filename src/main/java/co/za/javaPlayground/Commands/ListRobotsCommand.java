package co.za.javaPlayground.Commands;

import java.io.PrintStream;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListRobotsCommand {
    private final PrintStream out;
    private final CopyOnWriteArrayList<String> activeRobots;

    public ListRobotsCommand(PrintStream out, CopyOnWriteArrayList<String> activeRobots) {
        this.out = out;
        this.activeRobots = activeRobots;
    }

    public void execute() {
        out.println("Active robots: " + activeRobots);
    }
}
