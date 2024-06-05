package co.za.javaPlayground.Server;

import com.google.gson.JsonObject;

public class Robot {
    private final String name;
    private final String type;
    private int shields;
    private int shots;
    private int visibility;
    private int x;
    private int y;
    private String direction;
    private String status;

    private final Position TOP_LEFT = new Position(-200, 100);  // Update these values based on world size
    private final Position BOTTOM_RIGHT = new Position(200, -100); // Update these values based on world size

    public static final Position CENTRE = new Position(0, 0);

    public Robot(String name, String type, int shields, int shots, int visibility) {
        this.name = name;
        this.type = type;
        this.shields = shields;
        this.shots = shots;
        this.visibility = visibility;
        this.x = 0;
        this.y = 0;
        this.direction = "NORTH";
        this.status = "Ready";
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getShields() {
        return shields;
    }

    public int getShots() {
        return shots;
    }

    public int getVisibility() {
        return visibility;
    }

    public JsonObject getPositionAsJson() {
        JsonObject position = new JsonObject();
        position.addProperty("x", x);
        position.addProperty("y", y);
        return position;
    }

    public String getCurrentDirection() {
        return direction;
    }

    public void moveForward(int steps) {
        updatePosition(steps);
    }

    public void moveBack(int steps) {
        updatePosition(-steps);
    }

    private boolean updatePosition(int nrSteps) {
        int newX = this.x;
        int newY = this.y;

        switch (this.direction) {
            case "NORTH":
                newY += nrSteps;
                break;
            case "SOUTH":
                newY -= nrSteps;
                break;
            case "EAST":
                newX += nrSteps;
                break;
            case "WEST":
                newX -= nrSteps;
                break;
        }

        Position newPosition = new Position(newX, newY);
        if (newPosition.isIn(TOP_LEFT, BOTTOM_RIGHT)) {
            this.x = newX;
            this.y = newY;
            return true;
        }
        return false;
    }

    public void turn(String newDirection) {
        if ("left".equalsIgnoreCase(newDirection)) {
            switch (direction) {
                case "NORTH":
                    direction = "WEST";
                    break;
                case "WEST":
                    direction = "SOUTH";
                    break;
                case "SOUTH":
                    direction = "EAST";
                    break;
                case "EAST":
                    direction = "NORTH";
                    break;
            }
        } else if ("right".equalsIgnoreCase(newDirection)) {
            switch (direction) {
                case "NORTH":
                    direction = "EAST";
                    break;
                case "EAST":
                    direction = "SOUTH";
                    break;
                case "SOUTH":
                    direction = "WEST";
                    break;
                case "WEST":
                    direction = "NORTH";
                    break;
            }
        }
    }

    public boolean detectObstacle(Obstacle obstacle) {
        int obstacleX1 = obstacle.getX1();
        int obstacleY1 = obstacle.getY1();
        int obstacleX2 = obstacle.getX2();
        int obstacleY2 = obstacle.getY2();

        return (x >= obstacleX1 && x <= obstacleX2 && y >= obstacleY1 && y <= obstacleY2);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void handleCommand(String command, int steps) {
        if (command.equals("forward")) {
            moveForward(steps);
        } else if (command.equals("back")) {
            moveBack(steps);
        } else if (command.equals("turn")) {
            turn("left");
        }
    }
}
