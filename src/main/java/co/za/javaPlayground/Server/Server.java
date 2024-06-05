package co.za.javaPlayground.Server;

import Utilities.ServerConfig;
import co.za.javaPlayground.Commands.CommandHandler;

import java.io.PrintStream; // Ensure this import statement is present

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.ArrayList;
import java.util.List;


public class Server {
    private static final int DEFAULT_PORT = 1234;
    private static final String DEFAULT_IP_ADDRESS = "127.0.0.1";
    private static RobotFactory robotFactory;
    private static final List<Robot> activeRobots = new ArrayList<>();
    private static final List<PrintStream> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerConfig config = new ServerConfig("server.properties");
            String ipAddress = config.getProperty("server.ip");
            int port = config.getIntProperty("server.port");
            robotFactory = new RobotFactory("robots.properties");

            CommandHandler.initialize(robotFactory);
            runServer(ipAddress, port, config);
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading config file, proceeding with defaults: " + e.getMessage());
            runServer(DEFAULT_IP_ADDRESS, DEFAULT_PORT, null);
        }
    }

    private static void runServer(String ipAddress, int port, ServerConfig config) {
        try (ServerSocket serverSocket = new ServerSocket(port, 50, InetAddress.getByName(ipAddress))) {
            System.out.println("Server running & waiting for client connections.");

            List<Obstacle> obstacles = new ArrayList<>();
            if (config != null) {
                int numObstacles = config.getIntProperty("world.obstacles");
                for (int i = 1; i <= numObstacles; i++) {
                    String obstacleKey = "obstacle." + i;
                    String[] obstacleCoords = config.getProperty(obstacleKey).split(",");
                    int x1 = Integer.parseInt(obstacleCoords[0]);
                    int y1 = Integer.parseInt(obstacleCoords[1]);
                    int x2 = Integer.parseInt(obstacleCoords[2]);
                    int y2 = Integer.parseInt(obstacleCoords[3]);
                    obstacles.add(new SquareObstacle(x1, y1, x2 - x1));
                }
            }

            World world = new World(config.getIntProperty("world.width"), config.getIntProperty("world.height"), obstacles);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Connection: " + socket);
                Runnable clientHandler = new ClientCommandHandler(socket, world, activeRobots, clients);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            System.err.println("Could not bind to address: " + e.getMessage());
        }
    }
}
