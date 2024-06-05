package co.za.javaPlayground.Server;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;

public class World {
    private final int width;
    private final int height;
    private final List<Obstacle> obstacles;
    private final List<Robot> robots;

    public World(int width, int height, List<Obstacle> obstacles) {
        this.width = width;
        this.height = height;
        this.obstacles = obstacles;
        this.robots = new ArrayList<>();
    }

    public boolean launchRobot(String name, String type, int maxShieldStrength, int maxShots, int visibility) {
        for (Robot robot : robots) {
            if (robot.getName().equals(name)) {
                return false; // Robot with this name already exists
            }
        }
        Robot newRobot = new Robot(name, type, maxShieldStrength, maxShots, visibility);
        robots.add(newRobot);
        return true;
    }

    public boolean removeRobot(String robotName) {
        return robots.removeIf(robot -> robot.getName().equals(robotName));
    }

    public List<String> listRobots() {
        List<String> robotNames = new ArrayList<>();
        for (Robot robot : robots) {
            robotNames.add(robot.getName());
        }
        return robotNames;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public Robot getRobotByName(String name) {
        for (Robot robot : robots) {
            if (robot.getName().equals(name)) {
                return robot;
            }
        }
        return null;
    }

    public boolean detectObstacle(Position position) {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.blocksPosition(position)) {
                return true;
            }
        }
        return false;
    }
}
