package co.za.javaPlayground.Commands.Server;


import co.za.javaPlayground.Commands.Client.Command;
import co.za.javaPlayground.Server.World;
import Utilities.ResponseHandler;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

public class RobotsCommand implements Command {

    @Override
    public JsonObject execute(JsonObject request, World world) {
        JsonArray robotList = new JsonArray();
        for (String robotName : world.listRobots()) {
            robotList.add(robotName);
        }

        JsonObject data = new JsonObject();
        data.add("robots", robotList);

        return ResponseHandler.createSuccessResponse("Active robots: " + robotList.toString(), data);
    }
}
