package co.za.javaPlayground.Server;

import Utilities.ServerConfig;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class SimpleServer {
    public static final int DEFAULT_PORT = 1234;
    private static final CopyOnWriteArrayList<String> activeRobots = new CopyOnWriteArrayList<>();
    private static final CopyOnWriteArrayList<PrintStream> clients = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        try {
            ServerConfig config = new ServerConfig("server.properties");
            String ipAddress = config.getProperty("server.ip");
            int port = Integer.parseInt(config.getProperty("server.port"));
            runServer(ipAddress, port);
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading config file, using defaults: " + e.getMessage());
            runServer("127.0.0.1", DEFAULT_PORT);
        }
    }

    private static void runServer(String ipAddress, int port) {
        try (ServerSocket serverSocket = new ServerSocket(port, 50, InetAddress.getByName(ipAddress))) {
            System.out.println("Server running & waiting for client connections.");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Connection: " + socket);
                Runnable clientHandler = new ClientHandler(socket, activeRobots, clients);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            System.err.println("Could not bind to address: " + e.getMessage());
        }
    }
}
