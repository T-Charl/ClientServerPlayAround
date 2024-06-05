package co.za.javaPlayground.Commands.Client;


import co.za.javaPlayground.Server.Robot;
import co.za.javaPlayground.Server.World;
import Utilities.ResponseHandler;
import com.google.gson.JsonObject;

public class MoveCommand implements Command {
    private final String direction;

    public MoveCommand(String direction) {
        this.direction = direction;
    }

    @Override
    public JsonObject execute(JsonObject request, World world) {
        String robotName = request.get("robot").getAsString();
        int steps = request.get("arguments").getAsJsonArray().get(0).getAsInt();
        Robot robot = world.getRobotByName(robotName);

        if (robot != null) {
            if ("forward".equalsIgnoreCase(direction)) {
                robot.moveForward(steps);
            } else if ("back".equalsIgnoreCase(direction)) {
                robot.moveBack(steps);
            }
            JsonObject data = new JsonObject();
            data.add("position", robot.getPositionAsJson());
            data.addProperty("direction", robot.getCurrentDirection());
            data.addProperty("shields", robot.getShields());
            data.addProperty("shots", robot.getShots());
            data.addProperty("status", robot.getStatus());
            return ResponseHandler.createSuccessResponse("Robot " + robotName + " moved " + direction + " " + steps + " steps.", data);
        } else {
            return ResponseHandler.createErrorResponse("Robot " + robotName + " not found.");
        }
    }
}

