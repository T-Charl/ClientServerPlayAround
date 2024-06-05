package co.za.javaPlayground.Commands.Server;

//import java.io.PrintStream;
//import java.util.concurrent.CopyOnWriteArrayList;
//
//public class QuitCommand {
//    private final PrintStream out;
//    private final CopyOnWriteArrayList<String> activeRobots;
//    private final CopyOnWriteArrayList<PrintStream> clients;
//    private final String name;
//
//    public QuitCommand(PrintStream out, CopyOnWriteArrayList<String> activeRobots, CopyOnWriteArrayList<PrintStream> clients, String name) {
//        this.out = out;
//        this.activeRobots = activeRobots;
//        this.clients = clients;
//        this.name = name;
//    }
//
//    public void execute() {
//        if (activeRobots.remove(name)) {
//            out.println("Robot " + name + " has left the server.");
//            broadcastMessage("Robot " + name + " has left the server.");
//        } else {
//            out.println("Robot " + name + " does not exist.");
//        }
//    }
//
//    private void broadcastMessage(String message) {
//        for (PrintStream client : clients) {
//            client.println(message);
//        }
//    }
//}



//import co.za.javaPlayground.Commands.Client.Command;
//import co.za.javaPlayground.Server.World;
//import com.google.gson.JsonObject;
//
//public class QuitCommand implements Command {
//    private final String robotName;
//
//    public QuitCommand(String robotName) {
//        this.robotName = robotName;
//    }
//
//    @Override
//    public JsonObject execute(JsonObject request, World world) {
//        boolean success = world.removeRobot(robotName);
//        JsonObject response = new JsonObject();
//
//        if (success) {
//            response.addProperty("result", "OK");
//            JsonObject data = new JsonObject();
//            data.addProperty("message", "Robot " + robotName + " has left the server.");
//            response.add("data", data);
//        } else {
//            response = createErrorResponse("Failed to remove robot " + robotName);
//        }
//        return response;
//    }
//
//    private JsonObject createErrorResponse(String message) {
//        JsonObject response = new JsonObject();
//        response.addProperty("result", "ERROR");
//        JsonObject data = new JsonObject();
//        data.addProperty("message", message);
//        response.add("data", data);
//        return response;
//    }
//}





import co.za.javaPlayground.Commands.Client.Command;
import co.za.javaPlayground.Server.World;
import Utilities.ResponseHandler;
import com.google.gson.JsonObject;

public class QuitCommand implements Command {
    private final String robotName;

    public QuitCommand(String robotName) {
        this.robotName = robotName;
    }

    @Override
    public JsonObject execute(JsonObject request, World world) {
        boolean success = world.removeRobot(robotName);
        if (success) {
            return ResponseHandler.createSuccessResponse("Robot " + robotName + " has left the server.", null);
        } else {
            return ResponseHandler.createErrorResponse("Failed to remove robot " + robotName);
        }
    }
}
