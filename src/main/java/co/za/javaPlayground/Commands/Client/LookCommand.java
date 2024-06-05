package co.za.javaPlayground.Commands.Client;

import co.za.javaPlayground.Server.World;
import com.google.gson.JsonObject;

public class LookCommand implements Command {
    @Override
    public JsonObject execute(JsonObject request, World world) {
        JsonObject response = new JsonObject();
        response.addProperty("result", "OK");
        JsonObject data = new JsonObject();
        // Add logic to gather and include the objects seen by the robot
        response.add("data", data);
        return response;
    }
}
