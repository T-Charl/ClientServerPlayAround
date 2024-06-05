package co.za.javaPlayground.Commands;


import co.za.javaPlayground.Commands.Client.*;
import co.za.javaPlayground.Commands.Server.*;
import com.google.gson.JsonObject;

import co.za.javaPlayground.Server.RobotFactory;


public class CommandHandler {
    private static RobotFactory robotFactory;

    public static void initialize(RobotFactory factory) {
        robotFactory = factory;
    }

    public static Command getCommand(String commandName, JsonObject request) {
        switch (commandName.toLowerCase()) {
            case "launch":
                return new LaunchCommand(robotFactory);
            case "look":
                return new LookCommand();
            case "forward":
            case "back":
                return new MoveCommand(commandName);
            case "turn":
                return new TurnCommand(request.get("arguments").getAsJsonArray().get(0).getAsString());
            case "repair":
                return new RepairCommand();
            case "reload":
                return new ReloadCommand();
            case "fire":
                return new FireCommand();
            case "state":
                return new StateCommand();
            case "quit":
                return new QuitCommand(request.get("robot").getAsString());
            case "robots":
                return new RobotsCommand();
            default:
                throw new IllegalArgumentException("Unsupported command: " + commandName);
        }
    }
}
