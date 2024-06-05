package co.za.javaPlayground.Commands.Server;

import co.za.javaPlayground.Server.World;
import com.google.gson.JsonObject;

public interface Commands {
    JsonObject execute(JsonObject request, World world);
}
