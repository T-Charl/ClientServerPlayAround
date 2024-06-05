package co.za.javaPlayground.Commands.Client;

import com.google.gson.JsonObject;
import co.za.javaPlayground.Server.World;

public interface Command {
    JsonObject execute(JsonObject request, World world);
}
