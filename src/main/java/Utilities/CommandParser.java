package Utilities;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class CommandParser {

    public static JsonObject parseCommand(String commandString) {
        String[] parts = commandString.split(" ");
        String command = parts[0].toLowerCase();
        JsonObject request = new JsonObject();

        switch (command) {
            case "launch":
                if (parts.length != 3) {
                    throw new IllegalArgumentException("Invalid launch command. Usage: launch <name> <type>");
                }
                request.addProperty("robot", parts[1]);
                request.addProperty("command", "launch");
                JsonArray launchArgs = new JsonArray();
                launchArgs.add(parts[2]); // Type
                request.add("arguments", launchArgs);
                System.out.println("Tester!!Tester   " + request.toString());
                break;

            case "move":
                if (parts.length < 3) {
                    throw new IllegalArgumentException("Invalid move command. Usage: move <forward/back> <steps>");
                }
                request.addProperty("robot", parts[1]);
                request.addProperty("command", parts[0]);
                JsonArray moveArgs = new JsonArray();
                moveArgs.add(Integer.parseInt(parts[2]));
                request.add("arguments", moveArgs);
                break;

            case "turn":
                if (parts.length < 2) {
                    throw new IllegalArgumentException("Invalid turn command. Usage: turn <left/right>");
                }
                request.addProperty("robot", parts[1]);
                request.addProperty("command", "turn");
                JsonArray turnArgs = new JsonArray();
                turnArgs.add(parts[2]);
                request.add("arguments", turnArgs);
                break;

//            default:
//                request.addProperty("robot", parts.length > 1 ? parts[1] : "Robo1");
//                request.addProperty("command", command);
//                JsonArray args = new JsonArray();
//                for (int i = 1; i < parts.length; i++) {
//                    args.add(parts[i]);
//                }
//                request.add("arguments", args);
//                break;
        }

//        System.out.println("Parsed command!!!!! : " + request.toString()); // Debug statement

        return request;
    }
}
