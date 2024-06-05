package co.za.javaPlayground.Commands.Client;

import co.za.javaPlayground.Server.Robot;
import co.za.javaPlayground.Server.World;
import Utilities.ResponseHandler;
import com.google.gson.JsonObject;

public class TurnCommand implements Command {
    private final String direction;

    public TurnCommand(String direction) {
        this.direction = direction;
    }

    @Override
    public JsonObject execute(JsonObject request, World world) {
        String robotName = request.get("robot").getAsString();
        Robot robot = world.getRobotByName(robotName);

        if (robot != null) {
            robot.turn(direction);
            return ResponseHandler.createSuccessResponse("Robot " + robotName + " turned " + direction + ".", null);
        } else {
            return ResponseHandler.createErrorResponse("Robot " + robotName + " not found.");
        }
    }
}
