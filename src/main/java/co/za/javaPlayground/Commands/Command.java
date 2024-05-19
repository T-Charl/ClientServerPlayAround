package co.za.javaPlayground.Commands;

import java.io.PrintStream;

public abstract class Command {
    protected final PrintStream out;

    public Command(PrintStream out) {
        this.out = out;
    }

    public abstract void execute(String[] args);
}
