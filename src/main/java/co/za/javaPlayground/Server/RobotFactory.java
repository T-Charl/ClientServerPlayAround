package co.za.javaPlayground.Server;

import Utilities.ServerConfig;

import java.io.IOException;

public class RobotFactory {
    private ServerConfig config;

    public RobotFactory(String configFile) throws IOException {
        this.config = new ServerConfig(configFile);
    }

    public Robot createRobot(String name, String type) {
        String prefix = "robot." + type.toLowerCase() + ".";
        int shield = config.getIntProperty(prefix + "shield");
        int shots = config.getIntProperty(prefix + "shots");
        int visibility = config.getIntProperty(prefix + "visibility");

        return new Robot(name, type, shield, shots, visibility);
    }
}
