package co.za.javaPlayground.Commands.Client;

import co.za.javaPlayground.Server.World;
import com.google.gson.JsonObject;

import Utilities.ResponseHandler;
import co.za.javaPlayground.Server.RobotFactory;
import co.za.javaPlayground.Server.Robot;


public class LaunchCommand implements Command {
    private final RobotFactory robotFactory;

    public LaunchCommand(RobotFactory robotFactory) {
        this.robotFactory = robotFactory;
    }

    @Override
    public JsonObject execute(JsonObject request, World world) {
        JsonObject response;
        System.out.println("Executing launch command with request: " + request.toString()); // Debug statement

        if (!request.has("robot") || !request.has("arguments")) {
            return ResponseHandler.createErrorResponse("Missing required fields in request");
        }

        String robotName = request.get("robot").getAsString();
        String robotType = request.get("arguments").getAsJsonArray().get(0).getAsString();

        System.out.println("Robot Name: " + robotName); // Debug statement
        System.out.println("Robot Type: " + robotType); // Debug statement

        Robot robot = robotFactory.createRobot(robotName, robotType);
        if (robot == null) {
            return ResponseHandler.createErrorResponse("Invalid robot type: " + robotType);
        }

        if (world.launchRobot(robot.getName(), robot.getType(), robot.getShields(), robot.getShots(), robot.getVisibility())) {
            response = ResponseHandler.createSuccessResponse("Robot " + robotName + " launched successfully.", null);
        } else {
            response = ResponseHandler.createErrorResponse("Failed to launch robot " + robotName);
        }
        return response;
    }
}
